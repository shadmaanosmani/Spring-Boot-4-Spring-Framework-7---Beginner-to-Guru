package guru.springframework.springrestmvc.repository;

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import guru.springframework.springrestmvc.model.BeerStyle;
import guru.springframework.springrestmvc.model.entity.Beer;
import jakarta.validation.ConstraintViolationException;

@DataJpaTest
class BeerRepositoryTests {
	
	@Autowired
	BeerRepository beerRepository;
	
	@Test
	void saveBeerNameTooLong() {
		
		Beer beer = Beer.builder()
					    .beerName("My Beer 655169191651183168531351351321321321548454584")
					    .beerStyle(BeerStyle.ALE)
					    .upc("234234234234")
					    .price(new BigDecimal("11.99"))
					    .build();

		org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
				() -> beerRepository.saveAndFlush(beer));

	}
	
	@Test
	void saveBeer() {
		
		Beer beer = Beer.builder()
					    .beerName("My Beer 655169191651183168531351351321321321548454584")
					    .beerStyle(BeerStyle.ALE)
					    .upc("234234234234")
					    .price(new BigDecimal("11.99"))
					    .build();
		
		Beer savedBeer = beerRepository.save(beer);
		beerRepository.flush();
		
		Assertions.assertThat(savedBeer).isNotNull();
		Assertions.assertThat(savedBeer.getId()).isNotNull();
		
	}

}
