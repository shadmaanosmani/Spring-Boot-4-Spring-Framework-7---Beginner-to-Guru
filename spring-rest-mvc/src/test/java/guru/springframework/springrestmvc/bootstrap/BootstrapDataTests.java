package guru.springframework.springrestmvc.bootstrap;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.springrestmvc.repository.BeerRepository;
import guru.springframework.springrestmvc.repository.CustomerRepository;

@DataJpaTest
@ActiveProfiles(profiles = { "default" })
class BootstrapDataTests {
	
	@Autowired
	BeerRepository beerRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	BootstrapData bootstrapData;
	
	@BeforeEach
	void setUp() {
		
		this.bootstrapData = new BootstrapData(beerRepository, customerRepository);
		
	}
	
	@Test
	void run() throws Exception {

		bootstrapData.run();
		
		Assertions.assertThat(beerRepository.count()).isEqualTo(3);
		Assertions.assertThat(customerRepository.count()).isEqualTo(3);

	}

}
