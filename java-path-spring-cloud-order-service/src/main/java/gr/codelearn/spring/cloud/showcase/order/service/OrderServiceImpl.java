package gr.codelearn.spring.cloud.showcase.order.service;

import gr.codelearn.spring.cloud.showcase.core.domain.PaymentMethod;
import gr.codelearn.spring.cloud.showcase.core.service.BaseServiceImpl;
import gr.codelearn.spring.cloud.showcase.core.transfer.resource.CouponResource;
import gr.codelearn.spring.cloud.showcase.core.transfer.resource.CustomerResource;
import gr.codelearn.spring.cloud.showcase.core.transfer.resource.ProductResource;
import gr.codelearn.spring.cloud.showcase.order.domain.Order;
import gr.codelearn.spring.cloud.showcase.order.domain.OrderItem;
import gr.codelearn.spring.cloud.showcase.order.mapper.OrderMapper;
import gr.codelearn.spring.cloud.showcase.order.repository.OrderRepository;
import gr.codelearn.spring.cloud.showcase.order.service.client.LoyaltyServiceClient;
import gr.codelearn.spring.cloud.showcase.order.service.client.MailServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService {
	private final LoyaltyServiceClient loyaltyServiceClient;
	private final OrderRepository orderRepository;
	private final OrderMapper orderMapper;
	private final MailServiceClient mailServiceClient;

	@Override
	public JpaRepository<Order, Long> getRepository() {
		return orderRepository;
	}

	@Override
	public Order initiateOrder(CustomerResource customer) {
		return Order.builder().customerEmail(customer.getEmail()).customerCategory(customer.getCustomerCategory())
				.build();
	}

	@Override
	public void addItem(Order order, ProductResource product, int quantity) {
		if (checkNullability(order, product)) {
			return;
		}

		boolean increasedQuantity = false;

		// If product is already contained in the order, don't add it again, just increase the quantity accordingly
		for (OrderItem oi : order.getOrderItems()) {
			if (oi.getSerial().equals(product.getSerial())) {
				oi.setQuantity(oi.getQuantity() + quantity);
				increasedQuantity = true;
				break;
			}
		}

		if (!increasedQuantity) {
			order.getOrderItems().add(newOrderItem(order, product, quantity));
		}

		logger.debug("ProductResource[{}] added to Order[{}]", product, order);
	}

	private boolean checkNullability(Order order, ProductResource product) {
		if (order == null) {
			logger.warn("Order is null.");
			return true;
		}
		if (product == null) {
			logger.warn("ProductResource is null.");
			return true;
		}
		return false;
	}

	private OrderItem newOrderItem(Order order, ProductResource product, int quantity) {
		//@formatter:off
		return OrderItem.builder()
						.serial(product.getSerial())
						.name(product.getName())
						.categoryDescription(product.getCategory().getDescription())
						.order(order)
						.quantity(quantity)
						.price(product.getPrice()).build();
		//@formatter:on
	}

	@Override
	public void updateItem(Order order, ProductResource product, int quantity) {
		if (checkNullability(order, product)) {
			return;
		}

		order.getOrderItems().removeIf(oi -> oi.getSerial().equals(product.getSerial()));
		order.getOrderItems().add(newOrderItem(order, product, quantity));

		logger.debug("ProductResource[{}] updated in Order[{}]", product, order);
	}

	@Override
	public void deleteItem(Order order, ProductResource product) {
		if (checkNullability(order, product)) {
			return;
		}

		order.getOrderItems().removeIf(oi -> oi.getSerial().equals(product.getSerial()));
		logger.debug("ProductResource[{}] removed from Order[{}]", product, order);
	}

	@Override
	public Order checkout(Order order, PaymentMethod paymentMethod) {
		if (!validate(order)) {
			logger.warn("Order should have customer, order items, and payment type defined before being able to be " +
								"checkout the order.");
			return null;
		}

		// Set all order fields with proper values
		order.setPaymentMethod(paymentMethod);
		order.setSubmitDate(new Date());

		//Check for potential loyalty actions
		var coupon = Objects.requireNonNull(loyaltyServiceClient.apply(orderMapper.toResource(order)).getBody())
				.getData();

		order.setCost(giveDiscount(order, coupon));
		if (coupon != null) {
			order.setCouponCode(coupon.getCode());
		}

		var savedOrder = create(order);
		if (coupon != null) {
			loyaltyServiceClient.declare(coupon);
		}
		mailServiceClient.send(savedOrder.getCustomerEmail(),
							   String.format("Successfully submitted your order %d.", savedOrder.getId()),
							   String.format("You have successfully submitted your order with id %d costing %f at %tc.",
											 savedOrder.getId(), savedOrder.getCost(), savedOrder.getSubmitDate()));
		return savedOrder;
	}

	private boolean validate(Order order) {
		return order != null && !order.getOrderItems().isEmpty() && order.getCustomerEmail() != null &&
				order.getCustomerCategory() != null;
	}

	private BigDecimal giveDiscount(Order order, CouponResource coupon) {
		var totalDiscountPercent = order.getCustomerCategory().getDiscount() + order.getPaymentMethod().getDiscount();
		var explicitDiscountAmount = BigDecimal.ZERO;

		if (coupon != null) {
			totalDiscountPercent += coupon.getDiscountPercent();
			explicitDiscountAmount = coupon.getDiscountAmount();
		}

		//@formatter:off
		// Calculate original order cost
		var originalCost = order.getOrderItems().stream()
				.map(oi -> oi.getPrice().multiply(BigDecimal.valueOf(oi.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		//@formatter:on

		// Apply discounts
		var costAfterDiscount = originalCost.multiply(BigDecimal.valueOf(1F - totalDiscountPercent));
		costAfterDiscount = costAfterDiscount.subtract(explicitDiscountAmount);

		logger.debug("Order[{}], originalCost: {}, discountAmount: {}, totalDiscount: {}%, finalCost: {}.",
					 order.getId(), originalCost, explicitDiscountAmount, totalDiscountPercent * 100,
					 costAfterDiscount);

		return costAfterDiscount;
	}

	@Override
	public List<Order> findAll() {
		return orderRepository.findAllLazy();
	}

	public Order findLazy(Long id) {
		return orderRepository.findLazy(id);
	}
}
