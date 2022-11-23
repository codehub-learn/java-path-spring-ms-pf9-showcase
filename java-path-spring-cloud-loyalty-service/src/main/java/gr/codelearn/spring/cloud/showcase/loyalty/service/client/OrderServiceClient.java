package gr.codelearn.spring.cloud.showcase.loyalty.service.client;

import gr.codelearn.spring.cloud.showcase.core.transfer.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface OrderServiceClient {
	@GetMapping(headers = {"action=countByCustomer"})
	ResponseEntity<ApiResponse<Long>> countByCustomer(final String email);

	@GetMapping(headers = {"action=countByNotNullCouponCodeAndEmail"})
	ResponseEntity<ApiResponse<Long>> countByNotNullCouponCodeAndEmail(final String email);
}
