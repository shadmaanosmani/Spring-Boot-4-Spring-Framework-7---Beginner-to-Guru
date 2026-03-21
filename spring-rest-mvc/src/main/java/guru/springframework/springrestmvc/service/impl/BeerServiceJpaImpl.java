package guru.springframework.springrestmvc.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import guru.springframework.springrestmvc.mapper.BeerMapper;
import guru.springframework.springrestmvc.model.dto.BeerDTO;
import guru.springframework.springrestmvc.model.entity.Beer;
import guru.springframework.springrestmvc.repository.BeerRepository;
import guru.springframework.springrestmvc.service.BeerService;
import lombok.RequiredArgsConstructor;

@Primary
@Service
@RequiredArgsConstructor
public class BeerServiceJpaImpl implements BeerService {

	private final BeerRepository beerRepository;

	private final BeerMapper beerMapper;

	@Override
	public List<BeerDTO> listBeers() {

		return beerRepository.findAll()
							.stream()
							.map(beerMapper::beerToBeerDto)
							.toList();

	}

	@Override
	public Optional<BeerDTO> getBeerById(UUID beeriId) {
		
		return beerRepository.findById(beeriId)
							.map(beerMapper::beerToBeerDto);
		
	}

	@Override
	public BeerDTO saveNewBeer(BeerDTO beerDTO) {

		Beer beer = beerMapper.beerDtoToBeer(beerDTO);
		Beer savedBeer = beerRepository.save(beer);
		return beerMapper.beerToBeerDto(savedBeer);

	}

	@Override
	public Optional<BeerDTO> updateById(UUID beerId, BeerDTO beer) {

		AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

		beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {

			foundBeer.setBeerName(beer.getBeerName());
			foundBeer.setBeerStyle(beer.getBeerStyle());
			foundBeer.setUpc(beer.getUpc());
			foundBeer.setPrice(beer.getPrice());
			foundBeer.setUpdateDate(LocalDateTime.now());

			Beer savedBeer = beerRepository.save(foundBeer);

			atomicReference.set(Optional.ofNullable(beerMapper.beerToBeerDto(savedBeer)));

		}, () -> atomicReference.set(Optional.empty()));

		return atomicReference.get();

	}

	@Override
	public boolean deleteById(UUID beerId) {

		if (beerRepository.existsById(beerId)) {

			beerRepository.deleteById(beerId);
			return true;

		}

		return false;

	}

	@Override
	public boolean patchById(UUID beerId, BeerDTO beerDTO) {

		Optional<Beer> beer = beerRepository.findById(beerId);

		if (beer.isPresent()) {

			patchBeer(beer.get(), beerDTO);
			return true;

		}

		return false;

	}

	private void patchBeer(Beer beer, BeerDTO beerDTO) {

		boolean isUpdated = false;

		if (StringUtils.hasText(beerDTO.getBeerName())) {

			beer.setBeerName(beerDTO.getBeerName());
			isUpdated = true;

		}

		if (beerDTO.getBeerStyle() != null) {

			beer.setBeerStyle(beerDTO.getBeerStyle());
			isUpdated = true;

		}

		if (beerDTO.getPrice() != null) {

			beer.setPrice(beerDTO.getPrice());
			isUpdated = true;

		}

		if (beerDTO.getQuantityOnHand() != null) {

			beer.setQuantityOnHand(beerDTO.getQuantityOnHand());
			isUpdated = true;

		}

		if (StringUtils.hasText(beerDTO.getUpc())) {

			beer.setUpc(beerDTO.getUpc());
			isUpdated = true;

		}

		if (isUpdated) {

			beer.setUpdateDate(LocalDateTime.now());
			beerRepository.save(beer);

		}

	}

}
