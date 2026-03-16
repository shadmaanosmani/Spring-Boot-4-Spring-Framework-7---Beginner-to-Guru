package guru.springframework.springrestmvc.controller;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BeerControllerTests {

	@Autowired
	BeerController beerController;

	@Test
	void getBeerById() {

		IO.println(beerController.getBeerById(UUID.randomUUID()));

	}

}
