package guru.springframework.springrestmvc.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import guru.springframework.springrestmvc.model.dto.CustomerDTO;
import guru.springframework.springrestmvc.service.CustomerService;

@Service
public class CustomerServiceMapImpl implements CustomerService {

	private Map<UUID, CustomerDTO> customerMap;

	public CustomerServiceMapImpl() {
		
		customerMap = new HashMap<>();
		
		CustomerDTO customer1 = CustomerDTO.builder()
										.id(UUID.randomUUID())
										.name("Customer 1")
										.version(1)
										.createdDate(LocalDateTime.now())
										.lastModifiedDate(LocalDateTime.now())
										.build();
		
		CustomerDTO customer2 = CustomerDTO.builder()
										.id(UUID.randomUUID())
										.name("Customer 2")
										.version(1)
										.createdDate(LocalDateTime.now())
										.lastModifiedDate(LocalDateTime.now())
										.build();
		
		CustomerDTO customer3 = CustomerDTO.builder()
										.id(UUID.randomUUID())
										.name("Customer 3")
										.version(1)
										.createdDate(LocalDateTime.now())
										.lastModifiedDate(LocalDateTime.now())
										.build();
		
		customerMap.put(customer1.getId(), customer1);
		customerMap.put(customer2.getId(), customer2);
		customerMap.put(customer3.getId(), customer3);
		
	}

	@Override
	public List<CustomerDTO> listCustomers() {

		return new ArrayList<>(customerMap.values());

	}

	@Override
	public Optional<CustomerDTO> getCustomerById(UUID customerId) {

		return Optional.ofNullable(customerMap.get(customerId));

	}

	@Override
	public CustomerDTO saveNewCustomer(CustomerDTO customer) {

		CustomerDTO savedCustomer = CustomerDTO.builder()
								.id(UUID.randomUUID())
								.name(customer.getName())
								.version(1)
								.createdDate(LocalDateTime.now())
								.lastModifiedDate(LocalDateTime.now())
								.build();

		customerMap.put(savedCustomer.getId(), savedCustomer);

		return savedCustomer;

	}

	@Override
	public boolean updateCustomer(UUID customerId, CustomerDTO customer) {

		CustomerDTO savedCustomer = customerMap.get(customerId);

		savedCustomer.setName(customer.getName());
		savedCustomer.setLastModifiedDate(LocalDateTime.now());
		
		return true;

	}

	@Override
	public boolean deleteById(UUID customerId) {

		customerMap.remove(customerId);
		return true;

	}

	@Override
	public boolean patchCustomer(UUID customerId, CustomerDTO customer) {

		CustomerDTO savedCustomer = customerMap.get(customerId);

		if (StringUtils.hasText(customer.getName())) {

			savedCustomer.setName(customer.getName());
			savedCustomer.setLastModifiedDate(LocalDateTime.now());

		}
		
		return true;

	}

}
