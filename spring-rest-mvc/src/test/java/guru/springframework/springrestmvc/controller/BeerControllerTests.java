package guru.springframework.springrestmvc.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.assertj.core.api.AssertionsForClassTypes;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import guru.springframework.springrestmvc.model.Beer;
import guru.springframework.springrestmvc.service.BeerService;
import guru.springframework.springrestmvc.service.impl.BeerServiceImpl;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(BeerController.class)
@ExtendWith(MockitoExtension.class)
class BeerControllerTests {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;

	@MockitoBean
	BeerService beerService;
	
	@Captor
	ArgumentCaptor<UUID> uuidArgumentCaptor;
	
	@Captor
	ArgumentCaptor<Beer> beerArgumentCaptor;

	BeerServiceImpl beerServiceImpl;

	@BeforeEach
	void setUp() {

		this.beerServiceImpl = new BeerServiceImpl();

	}
	
	@Test
	void patchById() throws JacksonException, Exception {
		
		Beer beer = beerServiceImpl.listBeers().getFirst();
		
		Map<String, String> request = Map.of("beerName", "New Beer Name");
		
		mockMvc.perform(MockMvcRequestBuilders.patch(BeerController.BEER_PATH_WITH_ID, beer.getId())
											  .contentType(MediaType.APPLICATION_JSON)
											  .content(objectMapper.writeValueAsString(request))
											  .accept(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isNoContent());
		
		Mockito.verify(beerService).patchById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
		AssertionsForClassTypes.assertThat(uuidArgumentCaptor.getValue()).isEqualTo(beer.getId());
		AssertionsForClassTypes.assertThat(beerArgumentCaptor.getValue().getBeerName()).isEqualTo(request.get("beerName"));
		
	}

	@Test
	void deleteById() throws Exception {

		Beer beer = beerServiceImpl.listBeers().getFirst();

		mockMvc.perform(MockMvcRequestBuilders.delete(BeerController.BEER_PATH_WITH_ID, beer.getId())
											  .accept(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isNoContent());

		Mockito.verify(beerService).deleteById(uuidArgumentCaptor.capture());
		AssertionsForClassTypes.assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());

	}
	
	@Test
	void updateById() throws JacksonException, Exception {
		
		Beer beer = beerServiceImpl.listBeers().getFirst();
		
		mockMvc.perform(MockMvcRequestBuilders.put(BeerController.BEER_PATH_WITH_ID, beer.getId())
											  .contentType(MediaType.APPLICATION_JSON)
											  .content(objectMapper.writeValueAsString(beer))
											  .accept(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isNoContent());
		
		Mockito.verify(beerService).updateById(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(Beer.class));
		
	}

	@Test
	void handlePost() throws JacksonException, Exception {

		Beer beer = beerServiceImpl.listBeers().getFirst();
		
		BDDMockito.given(beerService.saveNewBeer(ArgumentMatchers.any(Beer.class)))
				  .willReturn(beer);
		
		mockMvc.perform(MockMvcRequestBuilders.post(BeerController.BEER_PATH)
											  .contentType(MediaType.APPLICATION_JSON)
											  .content(objectMapper.writeValueAsString(beer))
											  .accept(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isCreated())
			   .andExpect(MockMvcResultMatchers.header().exists("Location"));

	}
	
	@Test
	void listBeers() throws Exception {
		
		List<Beer> beers = beerServiceImpl.listBeers();
		
		BDDMockito.given(beerService.listBeers())
		  		  .willReturn(beers);
		
		mockMvc.perform(MockMvcRequestBuilders.get(BeerController.BEER_PATH)
						  		  			  .accept(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Is.is(beers.size())));
		
	}

	@Test
	void getBeerById() throws Exception {
		
		Beer testBeer = beerServiceImpl.listBeers().getFirst();
		
//		BDDMockito.given(beerService.getBeerById(ArgumentMatchers.any(UUID.class))).willReturn(testBeer);
		BDDMockito.given(beerService.getBeerById(testBeer.getId()))
				  .willReturn(testBeer);

		mockMvc.perform(MockMvcRequestBuilders.get(BeerController.BEER_PATH + "/" + testBeer.getId())
									  		  .accept(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(testBeer.getId().toString())))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.beerName", Is.is(testBeer.getBeerName())));

	}

}
