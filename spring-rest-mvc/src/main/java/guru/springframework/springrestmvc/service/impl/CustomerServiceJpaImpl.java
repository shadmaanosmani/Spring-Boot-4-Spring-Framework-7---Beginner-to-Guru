package guru.springframework.springrestmvc.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import guru.springframework.springrestmvc.mapper.CustomerMapper;
import guru.springframework.springrestmvc.model.dto.CustomerDTO;
import guru.springframework.springrestmvc.model.entity.Customer;
import guru.springframework.springrestmvc.repository.CustomerRepository;
import guru.springframework.springrestmvc.service.CustomerService;
import lombok.RequiredArgsConstructor;

@Primary
@Service
@RequiredArgsConstructor
public class CustomerServiceJpaImpl implements CustomerService {

	private final CustomerRepository customerRepository;

	private final CustomerMapper customerMapper;

	@Override
	public List<CustomerDTO> listCustomers() {
		
		return customerRepository.findAll()
								 .stream()
								 .map(customerMapper::customerToCustomerDto)
								 .toList();
		
	}

	@Override
	public Optional<CustomerDTO> getCustomerById(UUID customerId) {
		
		return customerRepository.findById(customerId)
								 .map(customerMapper::customerToCustomerDto);
		
	}

	@Override
	public CustomerDTO saveNewCustomer(CustomerDTO customerDTO) {

		Customer customer = customerMapper.customerDtotToCustomer(customerDTO);
		Customer savedCustomer = customerRepository.save(customer);
		return customerMapper.customerToCustomerDto(savedCustomer);

	}

	@Override
	public boolean updateCustomer(UUID customerId, CustomerDTO customerDTO) {

		Optional<Customer> customer = customerRepository.findById(customerId);

		if (customer.isPresent()) {

			updateCustomer(customer.get(), customerDTO);
			return true;

		}

		return false;

	}

	private void updateCustomer(Customer customer, CustomerDTO customerDTO) {

		customer.setName(customerDTO.getName());
		customer.setLastModifiedDate(LocalDateTime.now());

		customerRepository.save(customer);

	}

	@Override
	public boolean deleteById(UUID customerId) {

		if (customerRepository.existsById(customerId)) {

			customerRepository.deleteById(customerId);
			return true;

		}

		return false;

	}

	@Override
	public boolean patchCustomer(UUID customerId, CustomerDTO customerDTO) {

		Optional<Customer> customer = customerRepository.findById(customerId);

		if (customer.isPresent()) {

			patchCustomer(customer.get(), customerDTO);
			return true;

		}

		return false;

	}

	private void patchCustomer(Customer customer, CustomerDTO customerDTO) {

		if (StringUtils.hasText(customerDTO.getName())) {

			customer.setName(customerDTO.getName());
			customer.setLastModifiedDate(LocalDateTime.now());

			customerRepository.save(customer);

		}

	}

}
