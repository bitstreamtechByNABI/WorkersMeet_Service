package nabi.comworker.controller.registration;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import nabi.comworker.dto.AddressDTO;
import nabi.comworker.dto.AddressResponse;
import nabi.comworker.dto.DocumentUploadRequest;
import nabi.comworker.dto.documentApiResponse;
import nabi.comworker.model.ApplicationNumber;
import nabi.comworker.model.RegDocument;
import nabi.comworker.service.AddressService;
import nabi.comworker.service.ApplicationNumberService;
import nabi.comworker.service.impl.RegDocumentService;

@RestController
@RequestMapping("/api/address")
@Validated
public class AddressController {

	@Autowired
	private AddressService addressService;

	@Autowired
	private RegDocumentService regDocumentService;
	
	@Autowired
	private ApplicationNumberService applicationNumberService;

	@PostMapping("/employee")
	public ResponseEntity<AddressResponse> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
		Long id = addressService.saveAddress(addressDTO);
		AddressResponse response = new AddressResponse(id, "Address saved successfully");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping("/employee/document/upload")
	public ResponseEntity<documentApiResponse> uploadDocuments(@RequestBody DocumentUploadRequest request) {
	    try {
	        RegDocument savedDoc = regDocumentService.saveDocuments(request);

	        documentApiResponse response = new documentApiResponse(
	                "success",
	                "Documents saved successfully",
	                savedDoc.getId(),
	                LocalDateTime.now()  // Set current system date/time
	        );

	        return ResponseEntity.status(201).body(response);

	    } catch (IllegalArgumentException e) {
	    	documentApiResponse response = new documentApiResponse(
	                "error",
	                e.getMessage(),
	                null,
	                LocalDateTime.now()
	        );
	        return ResponseEntity.status(400).body(response);

	    } catch (Exception e) {
	    	documentApiResponse response = new documentApiResponse(
	                "error",
	                "Internal Server Error: " + e.getMessage(),
	                null,
	                LocalDateTime.now()
	        );
	        return ResponseEntity.status(500).body(response);
	    }
	}
	
	
	 @GetMapping("/generate-application-no")
	    public ResponseEntity<Map<String, Object>> generateApplicationNo(
	            @RequestParam Long documentId) {

	        // Pass documentId to the service
	        ApplicationNumber appNumber = applicationNumberService.generateApplicationNumber(documentId);

	        Map<String, Object> response = new HashMap<>();
	        response.put("status", 200);
	        response.put("message", "Application number generated successfully");
	        response.put("documentId", documentId);
	        response.put("applicationNo", appNumber.getApplicationNo());

	        return ResponseEntity.ok(response);
	    }


}
