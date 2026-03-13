package guru.springframework.spring_webapp.service.impl;

import org.springframework.stereotype.Service;

import guru.springframework.spring_webapp.domain.Author;
import guru.springframework.spring_webapp.repository.AuthorRepository;
import guru.springframework.spring_webapp.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

	private final AuthorRepository authorRepository;

	public AuthorServiceImpl(AuthorRepository authorRepository) {

		this.authorRepository = authorRepository;

	}

	@Override
	public Iterable<Author> findAll() {

		return authorRepository.findAll();

	}

}
