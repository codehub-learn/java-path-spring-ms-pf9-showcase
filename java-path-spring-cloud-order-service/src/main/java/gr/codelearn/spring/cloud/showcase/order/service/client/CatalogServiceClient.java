package gr.codelearn.spring.cloud.showcase.order.service.client;

import gr.codelearn.spring.cloud.showcase.core.transfer.ApiResponse;
import gr.codelearn.spring.cloud.showcase.core.transfer.resource.ProductResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface CatalogServiceClient {
	@GetMapping(params = {"serial"})
	ResponseEntity<ApiResponse<ProductResource>> findBySerial(@RequestParam(name = "serial") String serial);
}
