package guru.springframework.springrestmvc.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import guru.springframework.springrestmvc.model.entity.Beer;

@DataJpaTest
class BeerRepositoryTests {
	
	@Autowired
	BeerRepository beerRepository;
	
	@Test
	void saveBeer() {
		
		Beer savedBeer = beerRepository.save(Beer.builder()
												 .beerName("My Beer")
												 .build());
		
		Assertions.assertThat(savedBeer).isNotNull();
		Assertions.assertThat(savedBeer.getId()).isNotNull();
		
	}

}
