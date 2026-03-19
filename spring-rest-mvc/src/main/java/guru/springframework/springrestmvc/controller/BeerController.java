package guru.springframework.springrestmvc.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.springrestmvc.exception.NotFoundException;
import guru.springframework.springrestmvc.model.Beer;
import guru.springframework.springrestmvc.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(BeerController.BEER_PATH)
@RequiredArgsConstructor
public class BeerController {
	
	public static final String BEER_PATH = "/api/v1/beer";
	public static final String ID = "/{id}";
	public static final String DELIMITER = "/";
	public static final String BEER_PATH_WITH_ID = BEER_PATH + DELIMITER + ID;

	private final BeerService beerService;
	
	@PatchMapping(ID)
	public ResponseEntity<Beer> patchById(@PathVariable("id") UUID beerId, @RequestBody Beer beer) {
			
		beerService.patchById(beerId, beer);
		return ResponseEntity.noContent().build();
		
	}
	
	@DeleteMapping(ID)
	public ResponseEntity<Beer> deleteById(@PathVariable("id") UUID beerId) {
		
		beerService.deleteById(beerId);
		return ResponseEntity.noContent().build();
		
	}

	@PostMapping
	public ResponseEntity<Beer> handlePost(@RequestBody Beer beer) throws URISyntaxException {

		Beer savedBeer = beerService.saveNewBeer(beer);
		return ResponseEntity.created(new URI(BEER_PATH + DELIMITER + savedBeer.getId())).build();

	}

	@PutMapping(ID)
	public ResponseEntity<Beer> updateById(@PathVariable("id") UUID beerId, @RequestBody Beer beer) {

		beerService.updateById(beerId, beer);
		return ResponseEntity.noContent().build();

	}

	@GetMapping
	public List<Beer> listBeers() {

		log.debug("listBeers in controller was called");
		return beerService.listBeers();

	}

	@GetMapping(ID)
	public Beer getBeerById(@PathVariable("id") UUID beerId) {

		log.debug("getBeerById in controller was called");
		return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);

	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Void> handleNotFoundException() {
		
		return ResponseEntity.notFound().build();
		
	}

}
