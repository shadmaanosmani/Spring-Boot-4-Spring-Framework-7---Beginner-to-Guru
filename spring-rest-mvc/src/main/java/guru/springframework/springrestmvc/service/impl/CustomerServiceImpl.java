package guru.springframework.springrestmvc.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import guru.springframework.springrestmvc.model.Customer;
import guru.springframework.springrestmvc.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private Map<UUID, Customer> customerMap;

	public CustomerServiceImpl() {
		
		customerMap = new HashMap<>();
		
		Customer customer1 = Customer.builder()
							.id(UUID.randomUUID())
							.name("Customer 1")
							.version(1)
							.createdDate(LocalDateTime.now())
							.lastModifiedDate(LocalDateTime.now())
							.build();
		
		Customer customer2 = Customer.builder()
							.id(UUID.randomUUID())
							.name("Customer 2")
							.version(1)
							.createdDate(LocalDateTime.now())
							.lastModifiedDate(LocalDateTime.now())
							.build();
		
		Customer customer3 = Customer.builder()
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
	public List<Customer> listCustomers() {

		return new ArrayList<>(customerMap.values());

	}

	@Override
	public Customer getCustomerById(UUID customerId) {

		return customerMap.get(customerId);

	}

	@Override
	public Customer saveNewCustomer(Customer customer) {

		Customer savedCustomer = Customer.builder()
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
	public void updateCustomer(UUID customerId, Customer customer) {

		Customer savedCustomer = customerMap.get(customerId);

		savedCustomer.setName(customer.getName());
		savedCustomer.setLastModifiedDate(LocalDateTime.now());

	}

	@Override
	public void deleteById(UUID customerId) {

		customerMap.remove(customerId);

	}

	@Override
	public void patchCustomer(UUID customerId, Customer customer) {

		Customer savedCustomer = customerMap.get(customerId);

		if (StringUtils.hasText(customer.getName())) {

			savedCustomer.setName(customer.getName());
			savedCustomer.setLastModifiedDate(LocalDateTime.now());

		}

	}

}
