package guru.springframework.springrestmvc.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import guru.springframework.springrestmvc.model.Beer;
import guru.springframework.springrestmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

	@Override
	public Beer getBeerById(UUID beeriId) {

		log.debug("getBeerById in service was called");

		return Beer.builder()
				.id(beeriId)
				.version(1)
				.beerName("Galaxy Cat")
				.beerStyle(BeerStyle.PALE_ALE)
				.upc("123456")
				.price(new BigDecimal("12.99"))
				.quantityOnHand(122)
				.createdDate(LocalDateTime.now())
				.updateDate(LocalDateTime.now())
				.build();
		
	}

}
