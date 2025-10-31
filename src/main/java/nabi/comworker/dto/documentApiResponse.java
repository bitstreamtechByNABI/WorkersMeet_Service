package nabi.comworker.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class documentApiResponse {
	
	private String status;
	private String message;
	private Long documentId; 
	private LocalDateTime timestamp;

}
