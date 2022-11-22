package gr.codelearn.spring.cloud.showcase.core.transfer.resource;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString(callSuper = true)
public class CouponResource extends BaseModelResource {
	private String code;
	private Float discountPercent;
	private BigDecimal discountAmount;
	private Date generatedAt;
	private Date expiresAt;
	private Date usedAt;
}
