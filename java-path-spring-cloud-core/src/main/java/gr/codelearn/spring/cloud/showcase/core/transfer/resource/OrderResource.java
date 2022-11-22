package gr.codelearn.spring.cloud.showcase.core.transfer.resource;

import gr.codelearn.spring.cloud.showcase.core.domain.CustomerCategory;
import gr.codelearn.spring.cloud.showcase.core.domain.PaymentMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
public class OrderResource extends BaseModelResource {
	private String email;
	private CustomerCategory customerCategory;
	private final Set<OrderItemResource> orderItems = new HashSet<>();
	private PaymentMethod paymentMethod;
	private BigDecimal cost;
	private Date submitDate;
	private String couponCode;
}
