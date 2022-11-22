package gr.codelearn.spring.cloud.showcase.core.service;

import java.util.List;

public interface BaseService<T, ID> {
	T create(T item);

	List<T> createAll(List<T> items);

	void update(T item);

	void delete(T item);

	void deleteById(ID id);

	boolean exists(T item);

	T findById(ID id);

	List<T> findAll();

	Long count();
}
