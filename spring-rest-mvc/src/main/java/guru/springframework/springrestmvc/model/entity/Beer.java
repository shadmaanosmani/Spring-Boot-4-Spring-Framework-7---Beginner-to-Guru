package guru.springframework.springrestmvc.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import guru.springframework.springrestmvc.model.BeerStyle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Beer {

	@Id
	@JdbcTypeCode(SqlTypes.CHAR)
	@GeneratedValue(generator = "UUID")
	@UuidGenerator
	@Column(length = 36, columnDefinition = "VARCHAR(36)", updatable = false, nullable = false)
	private UUID id;
	
	@NotBlank
	@Size(max = 50)
	@Column(length = 50)
	private String beerName;

	@Version
	private Integer version;
	
	@NotNull
	private BeerStyle beerStyle;
	
	@NotBlank
	@Size(max = 255)
	private String upc;
	
	private Integer quantityOnHand;
	
	@NotNull
	private BigDecimal price;
	
	private LocalDateTime createdDate;
	private LocalDateTime updateDate;

}
