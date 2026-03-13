package guru.springframework.spring_webapp.repository;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.spring_webapp.domain.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}
