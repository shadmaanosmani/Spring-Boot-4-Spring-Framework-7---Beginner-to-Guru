package guru.springframework.springrestmvc.service;

import java.util.UUID;

import guru.springframework.springrestmvc.model.Beer;

public interface BeerService {
	
	Beer getBeerById(UUID beeriId);

}
