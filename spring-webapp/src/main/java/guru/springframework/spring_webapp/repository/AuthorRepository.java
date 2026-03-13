package guru.springframework.spring_webapp.repository;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.spring_webapp.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
