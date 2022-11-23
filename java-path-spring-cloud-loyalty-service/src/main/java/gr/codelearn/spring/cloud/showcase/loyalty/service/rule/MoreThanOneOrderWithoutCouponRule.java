package gr.codelearn.spring.cloud.showcase.loyalty.service.rule;

import gr.codelearn.spring.cloud.showcase.core.transfer.resource.OrderResource;
import gr.codelearn.spring.cloud.showcase.loyalty.service.client.OrderServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MoreThanOneOrderWithoutCouponRule implements Rule<OrderResource> {
	private final OrderServiceClient orderServiceClient;

	@Override
	public Float getDiscountPercent() {
		return 0.2F;
	}

	@Override
	public boolean matches(OrderResource order) {
		Long orderCount = orderServiceClient.countByCustomer(order.getCustomerEmail()).getBody().getData();
		Long orderWithCoupon = orderServiceClient.countByNotNullCouponCodeAndEmail(order.getCustomerEmail()).getBody()
				.getData();

		return orderCount > 1 && orderWithCoupon == 0;
	}

	@Override
	public Long getPriority() {
		return 100L;
	}
}
