package gr.codelearn.spring.cloud.showcase.core.base;

import java.util.List;

public interface BaseMapper<D, R> {
	R toResource(D domain);

	List<R> toResources(List<D> domains);

	D toDomain(R resource);

	List<D> toDomains(List<R> resources);
}
