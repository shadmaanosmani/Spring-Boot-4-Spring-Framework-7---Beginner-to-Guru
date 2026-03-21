package guru.springframework.springrestmvc.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import guru.springframework.springrestmvc.model.dto.BeerDTO;

public interface BeerService {

	List<BeerDTO> listBeers();

	Optional<BeerDTO> getBeerById(UUID beeriId);

	BeerDTO saveNewBeer(BeerDTO beer);

	Optional<BeerDTO> updateById(UUID beerId, BeerDTO beer);

	boolean deleteById(UUID beerId);

	boolean patchById(UUID beerId, BeerDTO beer);

}
