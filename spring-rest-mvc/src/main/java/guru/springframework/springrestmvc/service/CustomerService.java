package guru.springframework.springrestmvc.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import guru.springframework.springrestmvc.model.dto.CustomerDTO;

public interface CustomerService {

	List<CustomerDTO> listCustomers();

	Optional<CustomerDTO> getCustomerById(UUID customerId);

	CustomerDTO saveNewCustomer(CustomerDTO customer);

	boolean updateCustomer(UUID customerId, CustomerDTO customer);

	boolean deleteById(UUID customerId);

	boolean patchCustomer(UUID customerId, CustomerDTO customer);

}
