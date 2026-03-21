package guru.springframework.springrestmvc.bootstrap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.springrestmvc.model.BeerStyle;
import guru.springframework.springrestmvc.model.entity.Beer;
import guru.springframework.springrestmvc.model.entity.Customer;
import guru.springframework.springrestmvc.repository.BeerRepository;
import guru.springframework.springrestmvc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

	private final BeerRepository beerRepository;

	private final CustomerRepository customerRepository;

	@Override
	public void run(String... args) throws Exception {

		loadBeerData();
		loadCustomerData();

	}

	private void loadBeerData() {
		
		if (beerRepository.count() == 0) {
			
			Beer beer1 = Beer.builder()
							.beerName("Galaxy Cat")
							.beerStyle(BeerStyle.PALE_ALE)
							.upc("123456")
							.price(new BigDecimal("12.99"))
							.quantityOnHand(122)
							.createdDate(LocalDateTime.now())
							.build();

			Beer beer2 = Beer.builder()
							.beerName("Crank")
							.beerStyle(BeerStyle.PALE_ALE)
							.upc("12356222")
							.price(new BigDecimal("11.99"))
							.quantityOnHand(392)
							.createdDate(LocalDateTime.now())
							.build();
	
			Beer beer3 = Beer.builder()
							.beerName("Sunshine City")
							.beerStyle(BeerStyle.IPA)
							.upc("12356")
							.price(new BigDecimal("13.99"))
							.quantityOnHand(144)
							.createdDate(LocalDateTime.now())
							.build();
	
			beerRepository.save(beer1);
			beerRepository.save(beer2);
			beerRepository.save(beer3);
		
		}

	}

	private void loadCustomerData() {
	
		if (customerRepository.count() == 0) {
	
			Customer customer1 = Customer.builder()
										.name("Customer 1")
										.createdDate(LocalDateTime.now())
										.build();

			Customer customer2 = Customer.builder()
										.name("Customer 2")
										.createdDate(LocalDateTime.now())
										.build();
			
			Customer customer3 = Customer.builder()
										.name("Customer 3")
										.createdDate(LocalDateTime.now())
										.build();

			customerRepository.saveAll(List.of(customer1, customer2, customer3));

		}

	}

}
