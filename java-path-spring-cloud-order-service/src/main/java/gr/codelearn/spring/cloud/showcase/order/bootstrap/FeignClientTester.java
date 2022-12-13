package gr.codelearn.spring.cloud.showcase.order.bootstrap;

import gr.codelearn.spring.cloud.showcase.core.base.BaseComponent;
import gr.codelearn.spring.cloud.showcase.order.service.client.CatalogServiceClient;
import gr.codelearn.spring.cloud.showcase.order.service.client.CustomerServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Profile("feign")
@Component
@RequiredArgsConstructor
public class FeignClientTester extends BaseComponent implements CommandLineRunner {
	private final CatalogServiceClient catalogServiceClient;
	private final CustomerServiceClient customerServiceClient;

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

		logger.info("Round 3");
		logger.info("----------------");
		logger.info("Retrieving customer with id {}:, {}.", 1L, customerServiceClient.findById(1L));
		logger.info("Retrieving customer with email {}:, {}.", "c.giannacoulis@codehub.gr",
					customerServiceClient.findByEmail("c.giannacoulis@codehub.gr"));
		logger.info("----------------");
		logger.info("Retrieving customer with id {}:, {}.", 3L, customerServiceClient.findById(3L));
		logger.info("Retrieving customer with email {}:, {}.", "jones.pirves@gmailX.com",
					customerServiceClient.findByEmail("jones.pirves@gmailX.com"));
	}

	private void checkLoadBalancing() {
		logger.info("Check loadbalancing, {}.",
					Objects.requireNonNull(catalogServiceClient.checkLoadBalancing().getBody().getData()));
	}
}
