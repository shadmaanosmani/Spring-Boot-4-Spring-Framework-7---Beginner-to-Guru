package guru.springframework.springrestmvc.controller;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import guru.springframework.springrestmvc.exception.NotFoundException;
import guru.springframework.springrestmvc.mapper.BeerMapper;
import guru.springframework.springrestmvc.model.dto.BeerDTO;
import guru.springframework.springrestmvc.model.entity.Beer;
import guru.springframework.springrestmvc.repository.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
class BeerControllerIntegrationTests {

	@Autowired
	BeerController beerController;

	@Autowired
	BeerRepository beerRepository;
	
	@Autowired
	BeerMapper beerMapper;
	
	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
	ObjectMapper objectMapper;

	MockMvc mockMvc;

	@BeforeEach
	void setUp() {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}
	
	@Test
	void patchByIdBadName() throws JacksonException, Exception {

		Beer beer = beerRepository.findAll().getFirst();
		UUID beerId = beer.getId();
		final String newBeerName = "Updated beer name 321165313335211352111153333116551332115";
		Map<String, String> request = Map.of("beerName", newBeerName);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(BeerController.BEER_PATH_WITH_ID, beerId)
																    .contentType(MediaType.APPLICATION_JSON)
																    .content(objectMapper.writeValueAsString(request))
																    .accept(MediaType.APPLICATION_JSON))
									 .andExpect(MockMvcResultMatchers.status().isBadRequest())
									 .andReturn();
		
		log.debug("Response: " + mvcResult.getResponse().getContentAsString());

	}
	
	@Test
	void patchByIdNotFound() {

		UUID beerId = UUID.randomUUID();
		BeerDTO beerDTO = BeerDTO.builder().build();
		org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class,
				() -> beerController.patchById(beerId, beerDTO));

	}
	
	@Test
	@Rollback
	@Transactional
	void patchById() {

		Beer beer = beerRepository.findAll().getFirst();

		BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
		beerDTO.setId(null);
		beerDTO.setVersion(null);
		final String newBeerName = "Updated beer name";
		beerDTO.setBeerName(newBeerName);
		beerDTO.setBeerStyle(null);

		ResponseEntity<Void> response = beerController.patchById(beer.getId(), beerDTO);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

		BeerDTO updatedBeer = beerController.getBeerById(beer.getId());
		Assertions.assertThat(updatedBeer.getBeerName()).isEqualTo(newBeerName);
		Assertions.assertThat(updatedBeer.getBeerStyle()).isEqualTo(beer.getBeerStyle());

	}
	
	@Test
	void deleteByIdNotFound() {

		UUID beerId = UUID.randomUUID();

		org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> beerController.deleteById(beerId));

	}
	
	@Test
	@Rollback
	@Transactional
	void deleteById() {

		Beer beer = beerRepository.findAll().getFirst();
		UUID beerId = beer.getId();

		ResponseEntity<Void> response = beerController.deleteById(beerId);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

		org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class,
				() -> beerController.getBeerById(beerId));

	}
	
	@Test
	void updateBeerByIdNotFound() {

		UUID uuid = UUID.randomUUID();
		BeerDTO beerDTO = BeerDTO.builder()
								.beerName("Updated beer name")
								.build();

		org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class,
				() -> beerController.updateById(uuid, beerDTO));

	}
	
	@Test
	@Rollback
	@Transactional
	void updateBeerById() {

		Beer beer = beerRepository.findAll().getFirst();

		BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
		beerDTO.setId(null);
		beerDTO.setVersion(null);
		final String newBeerName = "Updated beer name";
		beerDTO.setBeerName(newBeerName);

		ResponseEntity<Void> response = beerController.updateById(beer.getId(), beerDTO);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

		BeerDTO updatedBeer = beerController.getBeerById(beer.getId());
		Assertions.assertThat(updatedBeer.getBeerName()).isEqualTo(newBeerName);

	}
	
	@Test
	@Rollback
	@Transactional
	void saveNewBeer() throws URISyntaxException {

		BeerDTO beerDTO = BeerDTO.builder()
								 .beerName("New Beer")
								 .build();
		ResponseEntity<Void> response = beerController.handlePost(beerDTO);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
		Assertions.assertThat(response.getHeaders().getLocation()).isNotNull();

		String[] location = response.getHeaders().getLocation().getPath().split("/");
		UUID savedUUID = UUID.fromString(location[4]);

		BeerDTO savedBeer = beerController.getBeerById(savedUUID);
		Assertions.assertThat(savedBeer).isNotNull();

	}

	@Test
	void listBeers() {

		List<BeerDTO> beerDTOs = beerController.listBeers();
		Assertions.assertThat(beerDTOs).hasSize(3);

	}

	@Test
	@Rollback
	@Transactional
	void emptyListBeers() {

		beerRepository.deleteAll();
		List<BeerDTO> beerDTOs = beerController.listBeers();
		Assertions.assertThat(beerDTOs).isEmpty();

	}

	@Test
	void getBeerById() {

		Beer beer = beerRepository.findAll().getFirst();
		BeerDTO beerDTO = beerController.getBeerById(beer.getId());
		Assertions.assertThat(beerDTO).isNotNull();

	}
	
	@Test
	void getBeerByIdNotFound() {

		UUID uuid = UUID.randomUUID();
		org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> beerController.getBeerById(uuid));

	}

}
