package nabi.comworker.service.impl;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nabi.comworker.dto.DocumentUploadRequest;
import nabi.comworker.model.Address;
import nabi.comworker.model.RegDocument;
import nabi.comworker.registration.repository.AddressRepository;
import nabi.comworker.registration.repository.RegDocumentRepository;

@Service
public class RegDocumentService {

    @Autowired
    private RegDocumentRepository regDocumentRepository;

    @Autowired
    private AddressRepository addressRepository;

    public RegDocument saveDocuments(DocumentUploadRequest request) {
        // Logger (using SLF4J)
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RegDocumentService.class);

        // Check if each key is present
        if (request.getAadhaar() == null || request.getAadhaar().isEmpty()) {
            logger.warn("Aadhaar file is missing in the request");
        }
        if (request.getPan() == null || request.getPan().isEmpty()) {
            logger.warn("PAN file is missing in the request");
        }
        if (request.getBankPassbook() == null || request.getBankPassbook().isEmpty()) {
            logger.warn("Bank Passbook file is missing in the request");
        }

        // Fetch Address entity
        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Address ID " + request.getAddressId() + " not found"));

        // Create RegDocument and set fields
        RegDocument doc = new RegDocument();
        doc.setAadhaar(decodeBase64(request.getAadhaar()));
        doc.setPan(decodeBase64(request.getPan()));
        doc.setBankPassbook(decodeBase64(request.getBankPassbook()));
        doc.setAddress(address);       // Set Address entity
        doc.setUserId("SYSTEM");       // Default userId (can be dynamic)

        // Save RegDocument
        return regDocumentRepository.save(doc);
    }


    private byte[] decodeBase64(String base64Data) {
        if (base64Data == null || base64Data.isEmpty()) return null;
        String[] parts = base64Data.split(",");
        String data = parts.length > 1 ? parts[1] : parts[0];
        return Base64.getDecoder().decode(data);
    }
}
