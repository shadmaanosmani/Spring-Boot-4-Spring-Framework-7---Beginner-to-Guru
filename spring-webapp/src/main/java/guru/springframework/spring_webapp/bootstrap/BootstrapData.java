package guru.springframework.spring_webapp.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.spring_webapp.domain.Author;
import guru.springframework.spring_webapp.domain.Book;
import guru.springframework.spring_webapp.domain.Publisher;
import guru.springframework.spring_webapp.repository.AuthorRepository;
import guru.springframework.spring_webapp.repository.BookRepository;
import guru.springframework.spring_webapp.repository.PublisherRepository;

@Component
public class BootstrapData implements CommandLineRunner {

	private final AuthorRepository authorRepository;
	private final BookRepository bookRepository;
	private final PublisherRepository publisherRepository;

	public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository,
			PublisherRepository publisherRepository) {

		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
		this.publisherRepository = publisherRepository;

	}

	@Override
	public void run(String... args) throws Exception {

		Author eric = new Author();
		eric.setFirstName("Eric");
		eric.setLastName("Evans");

		Book ddd = new Book();
		ddd.setTitle("Domain Driven Design");
		ddd.setIsbn("123456");

		Author ericSaved = authorRepository.save(eric);
		Book dddSaved = bookRepository.save(ddd);

		Author rod = new Author();
		rod.setFirstName("Rod");
		rod.setLastName("Johnson");

		Book noEJB = new Book();
		noEJB.setTitle("J2EE Development without EJB");
		noEJB.setIsbn("54757585");

		Author rodSaved = authorRepository.save(rod);
		Book noEJBSaved = bookRepository.save(noEJB);

		ericSaved.getBooks().add(dddSaved);
		rodSaved.getBooks().add(noEJBSaved);
		dddSaved.getAuthors().add(ericSaved);
		noEJBSaved.getAuthors().add(rodSaved);

		Publisher publisher = new Publisher();
		publisher.setPublisherName("MyPublisher");
		publisher.setAddress("123 Main Street");
		publisher.setCity("Hartford");
		publisher.setState("Connecticut");
		publisher.setZip("58501");

		Publisher publisherSaved = publisherRepository.save(publisher);

		dddSaved.setPublisher(publisherSaved);
		noEJBSaved.setPublisher(publisherSaved);

		authorRepository.save(ericSaved);
		authorRepository.save(rodSaved);
		bookRepository.save(dddSaved);
		bookRepository.save(noEJBSaved);

		IO.println("In Bootstrap");
		IO.println("Author count: " + authorRepository.count());
		IO.println("Book count: " + bookRepository.count());
		IO.println("Publisher count: " + publisherRepository.count());

	}

}
