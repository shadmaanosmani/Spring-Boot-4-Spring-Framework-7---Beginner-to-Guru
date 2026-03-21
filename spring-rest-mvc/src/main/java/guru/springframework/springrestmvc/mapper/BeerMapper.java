package guru.springframework.springrestmvc.mapper;

import org.mapstruct.Mapper;

import guru.springframework.springrestmvc.model.dto.BeerDTO;
import guru.springframework.springrestmvc.model.entity.Beer;

@Mapper
public interface BeerMapper {
	
	Beer beerDtoToBeer(BeerDTO beerDTO);
	
	BeerDTO beerToBeerDto(Beer beer);

}
