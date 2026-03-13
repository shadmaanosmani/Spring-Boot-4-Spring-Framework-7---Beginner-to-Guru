package guru.springframework.spring_webapp.service.impl;

import org.springframework.stereotype.Service;

import guru.springframework.spring_webapp.domain.Book;
import guru.springframework.spring_webapp.repository.BookRepository;
import guru.springframework.spring_webapp.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;

	public BookServiceImpl(BookRepository bookRepository) {

		this.bookRepository = bookRepository;

	}

	@Override
	public Iterable<Book> findAll() {

		return bookRepository.findAll();

	}

}
