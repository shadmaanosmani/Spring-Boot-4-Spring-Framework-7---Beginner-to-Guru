package guru.springframework.spring_webapp.service;

import guru.springframework.spring_webapp.domain.Author;

public interface AuthorService {

	Iterable<Author> findAll();

}
