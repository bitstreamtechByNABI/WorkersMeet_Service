package nabi.comworker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AddressDTO {

	@NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Police Station is required")
    private String policeStation;

    @NotBlank(message = "Post Office is required")
    private String postOffice;

    @NotBlank(message = "District is required")
    private String district;

    @NotBlank(message = "State is required")
    private String state;

    @Pattern(regexp = "\\d{6}", message = "Pin Code must be a 6-digit number")
    private String pinCode;
    
    private Long employeeId;
    
    
}
