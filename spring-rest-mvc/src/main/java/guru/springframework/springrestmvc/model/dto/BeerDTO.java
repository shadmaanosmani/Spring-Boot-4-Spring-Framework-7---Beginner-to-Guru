package guru.springframework.springrestmvc.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import guru.springframework.springrestmvc.model.BeerStyle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerDTO {

	private UUID id;
	private Integer version;

	@NotBlank
	private String beerName;

	@NotNull
	private BeerStyle beerStyle;

	@NotBlank
	private String upc;
	
	private Integer quantityOnHand;

	@NotNull
	private BigDecimal price;
	
	private LocalDateTime createdDate;
	private LocalDateTime updateDate;

}
