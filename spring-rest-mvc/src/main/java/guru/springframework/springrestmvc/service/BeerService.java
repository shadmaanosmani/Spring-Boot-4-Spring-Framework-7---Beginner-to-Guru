package guru.springframework.springrestmvc.service;

import java.util.List;
import java.util.UUID;

import guru.springframework.springrestmvc.model.Beer;

public interface BeerService {

	List<Beer> listBeers();

	Beer getBeerById(UUID beeriId);

	Beer saveNewBeer(Beer beer);

	void updateById(UUID beerId, Beer beer);

	void deleteById(UUID beerId);

	void patchById(UUID beerId, Beer beer);

}
