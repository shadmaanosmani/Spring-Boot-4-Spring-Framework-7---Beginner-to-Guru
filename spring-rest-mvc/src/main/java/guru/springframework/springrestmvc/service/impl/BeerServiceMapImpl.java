package guru.springframework.springrestmvc.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import guru.springframework.springrestmvc.model.BeerStyle;
import guru.springframework.springrestmvc.model.dto.BeerDTO;
import guru.springframework.springrestmvc.service.BeerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BeerServiceMapImpl implements BeerService {

	private Map<UUID, BeerDTO> beerMap;

	public BeerServiceMapImpl() {

		this.beerMap = new HashMap<>();

		BeerDTO beer1 = BeerDTO.builder()
							.id(UUID.randomUUID())
							.version(1)
							.beerName("Galaxy Cat")
							.beerStyle(BeerStyle.PALE_ALE)
							.upc("123456")
							.price(new BigDecimal("12.99"))
							.quantityOnHand(122)
							.createdDate(LocalDateTime.now())
							.updateDate(LocalDateTime.now())
							.build();
		
		BeerDTO beer2 = BeerDTO.builder()
							.id(UUID.randomUUID())
							.version(1)
							.beerName("Crank")
							.beerStyle(BeerStyle.PALE_ALE)
							.upc("12356222")
							.price(new BigDecimal("11.99"))
							.quantityOnHand(392)
							.createdDate(LocalDateTime.now())
							.updateDate(LocalDateTime.now())
							.build();
		
		BeerDTO beer3 = BeerDTO.builder()
							.id(UUID.randomUUID())
							.version(1)
							.beerName("Sunshine City")
							.beerStyle(BeerStyle.IPA)
							.upc("12356")
							.price(new BigDecimal("13.99"))
							.quantityOnHand(144)
							.createdDate(LocalDateTime.now())
							.updateDate(LocalDateTime.now())
							.build();

		beerMap.put(beer1.getId(), beer1);
		beerMap.put(beer2.getId(), beer2);
		beerMap.put(beer3.getId(), beer3);

	}

	@Override
	public List<BeerDTO> listBeers() {

		return new ArrayList<>(beerMap.values());

	}

	@Override
	public Optional<BeerDTO> getBeerById(UUID beeriId) {

		log.debug("getBeerById in service was called");
		return Optional.ofNullable(beerMap.get(beeriId));

	}

	@Override
	public BeerDTO saveNewBeer(BeerDTO beer) {
		
		BeerDTO savedBeer = BeerDTO.builder()
								.id(UUID.randomUUID())
								.version(1)
								.beerName(beer.getBeerName())
								.beerStyle(beer.getBeerStyle())
								.upc(beer.getUpc())
								.price(beer.getPrice())
								.quantityOnHand(beer.getQuantityOnHand())
								.createdDate(LocalDateTime.now())
								.updateDate(LocalDateTime.now())
								.build();

		beerMap.put(savedBeer.getId(), savedBeer);
		return savedBeer;

	}

	@Override
	public Optional<BeerDTO> updateById(UUID beerId, BeerDTO beer) {

		BeerDTO savedBeer = beerMap.get(beerId);

		savedBeer.setBeerName(beer.getBeerName());
		savedBeer.setBeerStyle(beer.getBeerStyle());
		savedBeer.setPrice(beer.getPrice());
		savedBeer.setQuantityOnHand(beer.getQuantityOnHand());
		savedBeer.setUpc(beer.getUpc());
		savedBeer.setUpdateDate(LocalDateTime.now());
		
		return Optional.ofNullable(savedBeer);

	}

	@Override
	public boolean deleteById(UUID beerId) {

		beerMap.remove(beerId);
		return true;

	}

	@Override
	public boolean patchById(UUID beerId, BeerDTO beer) {

		BeerDTO savedBeer = beerMap.get(beerId);

		boolean isUpdated = false;

		if (StringUtils.hasText(beer.getBeerName())) {

			savedBeer.setBeerName(beer.getBeerName());
			isUpdated = true;

		}

		if (beer.getBeerStyle() != null) {

			savedBeer.setBeerStyle(beer.getBeerStyle());
			isUpdated = true;

		}

		if (beer.getPrice() != null) {

			savedBeer.setPrice(beer.getPrice());
			isUpdated = true;

		}

		if (beer.getQuantityOnHand() != null) {

			savedBeer.setQuantityOnHand(beer.getQuantityOnHand());
			isUpdated = true;

		}

		if (StringUtils.hasText(beer.getUpc())) {

			savedBeer.setUpc(beer.getUpc());
			isUpdated = true;

		}

		if (isUpdated) {

			savedBeer.setUpdateDate(LocalDateTime.now());

		}
		
		return true;

	}

}
