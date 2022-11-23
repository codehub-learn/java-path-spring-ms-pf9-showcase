package gr.codelearn.spring.cloud.showcase.order.repository;

import gr.codelearn.spring.cloud.showcase.core.transfer.KeyValue;
import gr.codelearn.spring.cloud.showcase.order.domain.Order;
import gr.codelearn.spring.cloud.showcase.order.transfer.PurchasesPerCustomerCategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderReportRepository extends JpaRepository<Order, Long> {
	@Query("select new gr.codelearn.spring.cloud.showcase.core.transfer.KeyValue(o.customerEmail, avg(o.cost)) from Order o " +
			"group by o.customerEmail order by o.customerEmail")
	List<KeyValue<String, BigDecimal>> findAverageOrderCostPerCustomer();

	@Query("select count(o) from Order o where o.customerEmail = ?1")
	Long countByCustomer(String email);

	@Query("select count(o) from Order o where o.customerEmail = ?1 and o.couponCode is not null")
	Long countByNotNullCouponCodeAndEmail(String email);

	@Query()
	List<KeyValue<String, Integer>> findProductSaleFrequency();

	@Query(nativeQuery = true)
	List<PurchasesPerCustomerCategoryDto> findTotalNumberAndCostOfPurchasesPerCustomerCategory();
}
