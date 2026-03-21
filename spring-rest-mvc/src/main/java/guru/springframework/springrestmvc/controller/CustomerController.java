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

import guru.springframework.springrestmvc.exception.NotFoundException;
import guru.springframework.springrestmvc.model.dto.CustomerDTO;
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
	public static final String DELIMITER = "/";
	public static final String CUSTOMER_PATH_WITH_ID = CUSTOMER_PATH + DELIMITER + ID;

	private final CustomerService customerService;

	@PatchMapping(ID)
	public ResponseEntity<Void> patchById(@PathVariable("id") UUID customerId, @RequestBody CustomerDTO customer) {

		if (!customerService.patchCustomer(customerId, customer)) {
			throw new NotFoundException();
		}

		return ResponseEntity.noContent().build();

	}
	
	@DeleteMapping(ID)
	public ResponseEntity<Void> deleteById(@PathVariable("id") UUID customerId) {

		if (!customerService.deleteById(customerId)) {
			throw new NotFoundException();
		}

		return ResponseEntity.noContent().build();

	}
	
	@PutMapping(ID)
	public ResponseEntity<Void> updateCustomer(@PathVariable("id") UUID customerId,
			@RequestBody CustomerDTO customer) {

		if (!customerService.updateCustomer(customerId, customer)) {
			throw new NotFoundException();
		}

		return ResponseEntity.noContent().build();

	}
	
	@GetMapping
	public List<CustomerDTO> listCustomers() {

		return customerService.listCustomers();

	}

	@GetMapping(ID)
	public CustomerDTO getCustomerById(@PathVariable("id") UUID customerId) {

		return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);

	}

	@PostMapping
	public ResponseEntity<Void> handlePost(@RequestBody CustomerDTO customer) throws URISyntaxException {

		CustomerDTO savedCustomer = customerService.saveNewCustomer(customer);
		return ResponseEntity.created(new URI(CUSTOMER_PATH + DELIMITER + savedCustomer.getId())).build();

	}

}
