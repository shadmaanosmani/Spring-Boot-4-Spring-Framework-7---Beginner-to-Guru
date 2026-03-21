package guru.springframework.springrestmvc.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.springrestmvc.model.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

}
