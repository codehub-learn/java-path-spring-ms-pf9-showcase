package gr.codelearn.spring.cloud.showcase.core.service;

import gr.codelearn.spring.cloud.showcase.core.base.BaseComponent;
import gr.codelearn.spring.cloud.showcase.core.domain.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public abstract class BaseServiceImpl<T extends BaseModel> extends BaseComponent implements BaseService<T, Long> {
	public abstract JpaRepository<T, Long> getRepository();

	public T create(T item) {
		logger.trace("Creating {}.", item);
		return getRepository().save(item);
	}

	public List<T> createAll(List<T> items) {
		logger.trace("Creating {}.", items);
		return getRepository().saveAll(items);
	}

	public void update(T item) {
		logger.trace("Updating {}.", item);
		if (exists(item.getId())) {
			getRepository().save(item);
		} else {
			throw new NoSuchElementException("Could not perform update operation to a non-existent object.");
		}
	}

	public void delete(T item) {
		logger.trace("Deleting {}.", item);
		if (exists(item.getId())) {
			getRepository().delete(item);
		} else {
			throw new NoSuchElementException("Could not perform delete operation to a non-existent object.");
		}
	}

	public void deleteById(Long id) {
		logger.trace("Deleting entity matching id {}.", id);
		if (exists(id)) {
			getRepository().deleteById(id);
		} else {
			throw new NoSuchElementException("Could not perform delete operation to a non-existent object.");
		}
	}

	public boolean exists(T item) {
		logger.trace("Checking if {} exists.", item);
		return exists(item.getId());
	}

	@Transactional(readOnly = true)
	public boolean exists(Long id) {
		return getRepository().existsById(id);
	}

	@Transactional(readOnly = true)
	public T findById(Long id) {
		T item = getRepository().findById(id).orElseThrow();
		logger.trace("Item found matching id:{}.", id);
		return item;
	}

	@Transactional(readOnly = true)
	public List<T> findAll() {
		logger.trace("Retrieving all items.");
		return getRepository().findAll();
	}

	@Transactional(readOnly = true)
	public Long count() {
		return getRepository().count();
	}
}
