package nabi.comworker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentUploadRequest {

	private String aadhaar;
	private String pan;
	private String bankPassbook;
	private Long addressId;

}
