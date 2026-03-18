package guru.springframework.springrestmvc.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.springrestmvc.model.Customer;
import guru.springframework.springrestmvc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(CustomerController.CUSTOMER_PATH)
public class CustomerController {
	
	public static final String CUSTOMER_PATH = "/api/v1/customer";
	public static final String ID = "/{id}";
	public static final String CUSTOMER_PATH_WITH_ID = CUSTOMER_PATH + "/" + ID;

	private final CustomerService customerService;

	@PatchMapping(ID)
	public ResponseEntity<Customer> patchById(@PathVariable("id") UUID customerId, @RequestBody Customer customer) {

		customerService.patchCustomer(customerId, customer);
		return ResponseEntity.noContent().build();

	}
	
	@DeleteMapping(ID)
	public ResponseEntity<Customer> deleteById(@PathVariable("id") UUID customerId) {
		
		customerService.deleteById(customerId);
		return ResponseEntity.noContent().build();
		
	}
	
	@PutMapping(ID)
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") UUID customerId,
			@RequestBody Customer customer) {

		customerService.updateCustomer(customerId, customer);
		return ResponseEntity.noContent().build();

	}
	
	@GetMapping
	public List<Customer> listCustomers() {

		return customerService.listCustomers();

	}

	@GetMapping(ID)
	public Customer getCustomerById(@PathVariable("id") UUID customerId) {

		return customerService.getCustomerById(customerId);

	}

	@PostMapping
	public ResponseEntity<Customer> handlePost(@RequestBody Customer customer) throws URISyntaxException {

		Customer savedCustomer = customerService.saveNewCustomer(customer);
		return ResponseEntity.created(new URI("/api/v1/customer/" + savedCustomer.getId())).build();

	}

}
