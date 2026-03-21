package guru.springframework.springrestmvc.mapper;

import org.mapstruct.Mapper;

import guru.springframework.springrestmvc.model.dto.CustomerDTO;
import guru.springframework.springrestmvc.model.entity.Customer;

@Mapper
public interface CustomerMapper {

	Customer customerDtotToCustomer(CustomerDTO customerDTO);

	CustomerDTO customerToCustomerDto(Customer customer);

}
