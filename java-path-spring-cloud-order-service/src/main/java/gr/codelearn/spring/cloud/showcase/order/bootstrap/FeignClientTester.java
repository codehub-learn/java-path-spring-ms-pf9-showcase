package gr.codelearn.spring.cloud.showcase.order.bootstrap;

import gr.codelearn.spring.cloud.showcase.core.base.BaseComponent;
import gr.codelearn.spring.cloud.showcase.order.service.client.CatalogServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FeignClientTester extends BaseComponent implements CommandLineRunner {
	private final CatalogServiceClient catalogServiceClient;

	@Override
	public void run(final String... args) throws Exception {
		logger.info("Round 1");
		logger.info("----------------");
		checkLoadBalancing();
		checkLoadBalancing();
		checkLoadBalancing();
		checkLoadBalancing();
		checkLoadBalancing();
		checkLoadBalancing();
		checkLoadBalancing();
		checkLoadBalancing();
		checkLoadBalancing();

		logger.info("Round 2");
		logger.info("----------------");
		checkLoadBalancing();
		checkLoadBalancing();
		checkLoadBalancing();
		checkLoadBalancing();
		checkLoadBalancing();
		checkLoadBalancing();
		checkLoadBalancing();
		checkLoadBalancing();
		checkLoadBalancing();
	}

	private void checkLoadBalancing() {
		logger.info("Check loadbalancing, {}.",
					Objects.requireNonNull(catalogServiceClient.checkLoadBalancing().getBody().getData()));
	}
}
