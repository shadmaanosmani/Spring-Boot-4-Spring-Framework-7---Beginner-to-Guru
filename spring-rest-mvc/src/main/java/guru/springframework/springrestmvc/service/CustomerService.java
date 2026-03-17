package guru.springframework.springrestmvc.service;

import java.util.List;
import java.util.UUID;

import guru.springframework.springrestmvc.model.Customer;

public interface CustomerService {

	List<Customer> listCustomers();

	Customer getCustomerById(UUID customerId);

	Customer saveNewCustomer(Customer customer);

	void updateCustomer(UUID customerId, Customer customer);

	void deleteById(UUID customerId);

	void patchCustomer(UUID customerId, Customer customer);

}
