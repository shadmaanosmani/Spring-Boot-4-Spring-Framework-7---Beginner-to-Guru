package guru.springframework.springrestmvc.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import guru.springframework.springrestmvc.model.entity.Customer;

@DataJpaTest(showSql = true)
class CustomerRepositoryTests {

	@Autowired
	CustomerRepository customerRepository;

	@Test
	void saveCustomer() {

		Customer savedCustomer = customerRepository.save(Customer.builder()
																 .name("OIRUBOTU")
																 .build());

		Assertions.assertThat(savedCustomer).isNotNull();
		Assertions.assertThat(savedCustomer.getId()).isNotNull();

	}

}
