package guru.springframework.springrestmvc.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

	private UUID id;
	private String name;
	private Integer version;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;

}
