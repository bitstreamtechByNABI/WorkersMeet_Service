package nabi.comworker.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
	
	  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
	    private Long id; 

    @NotBlank(message = "Candidate name is required")
    private String candidateName;

    @NotBlank(message = "Mother's name is required")
    private String motherName;

    @NotBlank(message = "Father's name is required")
    private String fatherName;

    @NotNull(message = "Date of birth is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Mobile number is required")
    private String mobileNumber;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    // Optional fields for response only — ignore if sent by client
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedDate;
}
