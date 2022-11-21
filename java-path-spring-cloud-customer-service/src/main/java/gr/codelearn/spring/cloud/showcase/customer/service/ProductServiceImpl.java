package gr.codelearn.spring.cloud.showcase.customer.service;

import gr.codelearn.spring.cloud.showcase.customer.domain.Product;
import gr.codelearn.spring.cloud.showcase.customer.repository.ProductRepository;
import gr.codelearn.spring.cloud.showcase.customer.transfer.KeyValue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {
	private final ProductRepository productRepository;

	@Override
	public JpaRepository<Product, Long> getRepository() {
		return productRepository;
	}

	@Override
	public Product findBySerial(String serial) {
		return productRepository.findBySerial(serial);
	}

	@Override
	public List<KeyValue<String, Integer>> findProductSaleFrequency() {
		return productRepository.findProductSaleFrequency();
	}
}
