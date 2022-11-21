package gr.codelearn.spring.cloud.showcase.catalog.repository;

import gr.codelearn.spring.cloud.showcase.catalog.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	@Query("select o from Order o join fetch o.orderItems where o.id = ?1")
	Order findLazy(Long id);

	@Query("select distinct o from Order o join fetch o.orderItems")
	List<Order> findAllLazy();
}
