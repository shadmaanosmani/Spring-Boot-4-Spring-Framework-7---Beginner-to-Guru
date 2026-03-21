package guru.springframework.springrestmvc.controller;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.springrestmvc.exception.NotFoundException;
import guru.springframework.springrestmvc.mapper.CustomerMapper;
import guru.springframework.springrestmvc.model.dto.CustomerDTO;
import guru.springframework.springrestmvc.model.entity.Customer;
import guru.springframework.springrestmvc.repository.CustomerRepository;

@SpringBootTest
class CustomerControllerIntegrationTests {

	@Autowired
	CustomerController customerController;

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerMapper customerMapper;
	
	@Test
	void patchByIdNotFound() {

		UUID uuid = UUID.randomUUID();
		CustomerDTO customerDTO = CustomerDTO.builder().build();

		org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class,
				() -> customerController.patchById(uuid, customerDTO));

	}
	
	@Test
	@Rollback
	@Transactional
	void patchById() {

		Customer customer = customerRepository.findAll().getFirst();
		UUID customerId = customer.getId();
		CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
		customerDTO.setId(null);
		final String newCustomerName = "New customer name";
		customerDTO.setName(newCustomerName);

		ResponseEntity<Void> response = customerController.patchById(customerId, customerDTO);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

		CustomerDTO savedCustomer = customerController.getCustomerById(customerId);
		Assertions.assertThat(savedCustomer.getName()).isEqualTo(newCustomerName);

	}
	
	@Test
	void deleteByIdNotFound() {

		UUID uuid = UUID.randomUUID();

		org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class,
				() -> customerController.deleteById(uuid));

	}

	@Test
	@Rollback
	@Transactional
	void deleteById() {

		Customer customer = customerRepository.findAll().getFirst();
		UUID customerId = customer.getId();

		ResponseEntity<Void> response = customerController.deleteById(customerId);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

		org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class,
				() -> customerController.getCustomerById(customerId));

	}
	
	@Test
	void updateCustomerNotFound() {

		UUID customerId = UUID.randomUUID();
		CustomerDTO customerDTO = CustomerDTO.builder().build();

		org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class,
				() -> customerController.updateCustomer(customerId, customerDTO));

	}
	
	@Test
	@Rollback
	@Transactional
	void updateCustomer() {

		Customer customer = customerRepository.findAll().getFirst();
		UUID customerId = customer.getId();
		CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
		customerDTO.setId(null);
		customerDTO.setVersion(null);
		final String newCustomerName = "New name";
		customerDTO.setName(newCustomerName);

		ResponseEntity<Void> response = customerController.updateCustomer(customerId, customerDTO);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

		CustomerDTO savedCustomer = customerController.getCustomerById(customerId);
		Assertions.assertThat(savedCustomer).isNotNull();
		Assertions.assertThat(savedCustomer.getName()).isEqualTo(newCustomerName);

	}
	
	@Test
	@Rollback
	@Transactional
	void handlePost() throws URISyntaxException {
		
		CustomerDTO customerDTO = CustomerDTO.builder()
											 .name("New customer")
											 .build();
		ResponseEntity<Void> response = customerController.handlePost(customerDTO);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
		Assertions.assertThat(response.getHeaders().getLocation()).isNotNull();
		
		String[] location = response.getHeaders().getLocation().getPath().split("/");
		UUID customerId = UUID.fromString(location[4]);
		
		CustomerDTO savedCustomer = customerController.getCustomerById(customerId);
		Assertions.assertThat(savedCustomer).isNotNull();
		
	}

	@Test
	void listCustomers() {

		List<CustomerDTO> customerDTOs = customerController.listCustomers();
		Assertions.assertThat(customerDTOs).hasSize(3);

	}

	@Test
	@Rollback
	@Transactional
	void emptyListCustomers() {

		customerRepository.deleteAll();
		List<CustomerDTO> customerDTOs = customerController.listCustomers();
		Assertions.assertThat(customerDTOs).isEmpty();

	}

	@Test
	void getCustomerById() {

		Customer customer = customerRepository.findAll().getFirst();
		CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
		Assertions.assertThat(customerDTO).isNotNull();

	}

	@Test
	void getCustomerByIdNotFound() {

		UUID uuid = UUID.randomUUID();
		org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class,
				() -> customerController.getCustomerById(uuid));

	}

}
