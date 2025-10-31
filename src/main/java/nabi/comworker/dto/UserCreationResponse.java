package nabi.comworker.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreationResponse {

	private String message;
	private Long id;
	private LocalDateTime createdDate;

}
