package gr.codelearn.spring.cloud.showcase.customer.service;

import gr.codelearn.spring.cloud.showcase.core.service.BaseServiceImpl;
import gr.codelearn.spring.cloud.showcase.customer.domain.Customer;
import gr.codelearn.spring.cloud.showcase.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl extends BaseServiceImpl<Customer> implements CustomerService {
	private final CustomerRepository customerRepository;

	@Override
	public JpaRepository<Customer, Long> getRepository() {
		return customerRepository;
	}

	@Override
	public Customer findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

//	@Override
//	public List<KeyValue<String, BigDecimal>> findCustomersPurchasedMostExpensiveProduct() {
//		return customerRepository.findCustomersPurchasedMostExpensiveProduct();
//	}

//	@Override
//	public List<PurchasesPerCustomerCategoryDto> findTotalNumberAndCostOfPurchasesPerCustomerCategory() {
//		return customerRepository.findTotalNumberAndCostOfPurchasesPerCustomerCategory();
//	}
}
