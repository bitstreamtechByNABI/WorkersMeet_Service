package nabi.comworker.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {

	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String message;
	private String path;

}
