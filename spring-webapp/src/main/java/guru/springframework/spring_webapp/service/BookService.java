package guru.springframework.spring_webapp.service;

import guru.springframework.spring_webapp.domain.Book;

public interface BookService {

	Iterable<Book> findAll();

}
