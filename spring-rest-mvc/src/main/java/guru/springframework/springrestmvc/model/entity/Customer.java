package guru.springframework.springrestmvc.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
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
public class Customer {

	@Id
	@GeneratedValue(generator = "UUID")
	@UuidGenerator
	@Column(length = 36, columnDefinition = "VARCHAR", updatable = false, nullable = false)
	private UUID id;
	private String name;

	@Version
	private Integer version;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;

}
