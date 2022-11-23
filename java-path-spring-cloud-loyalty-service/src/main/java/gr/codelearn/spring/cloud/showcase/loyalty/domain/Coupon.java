package gr.codelearn.spring.cloud.showcase.loyalty.domain;

import gr.codelearn.spring.cloud.showcase.core.domain.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COUPONS")
@SequenceGenerator(name = "idGenerator", sequenceName = "COUPONS_SEQ", initialValue = 1, allocationSize = 1)
public class Coupon extends BaseModel {
	@NotNull
	@Column(length = 36, nullable = false)
	private String code;

	@Column(name = "DISCOUNT_PERCENT")
	private Float discountPercent;

	@Column(name = "DISCOUNT_AMOUNT", precision = 10, scale = 2)
	private BigDecimal discountAmount;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "GENERATED_AT", nullable = false)
	private Date generatedAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIRES_AT")
	private Date expiresAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "USED_AT")
	private Date usedAt;
}
