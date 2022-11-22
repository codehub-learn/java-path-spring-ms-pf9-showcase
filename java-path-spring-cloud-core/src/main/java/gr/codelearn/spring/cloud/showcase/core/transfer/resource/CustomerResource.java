package gr.codelearn.spring.cloud.showcase.core.transfer.resource;

import gr.codelearn.spring.cloud.showcase.core.domain.CustomerCategory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class CustomerResource extends BaseModelResource {
	private String email;
	private String firstname;
	private String lastname;
	private Integer age;
	private String address;
	private CustomerCategory customerCategory;
}
