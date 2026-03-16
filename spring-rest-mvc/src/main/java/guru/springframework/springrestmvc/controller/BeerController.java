package guru.springframework.springrestmvc.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;

import guru.springframework.springrestmvc.model.Beer;
import guru.springframework.springrestmvc.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BeerController {

	private final BeerService beerService;

	public Beer getBeerById(UUID beerId) {

		log.debug("getBeerById in controller was called");
		return beerService.getBeerById(beerId);

	}

}
