package guru.springframework.spring_webapp.repository;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.spring_webapp.domain.Publisher;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
}
