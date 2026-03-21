package guru.springframework.springrestmvc.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.springrestmvc.model.entity.Beer;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

}
