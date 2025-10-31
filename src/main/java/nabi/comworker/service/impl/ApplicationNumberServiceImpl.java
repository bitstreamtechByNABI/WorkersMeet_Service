package nabi.comworker.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nabi.comworker.dto.ApplicationNumberPrefix;
import nabi.comworker.model.ApplicationNumber;
import nabi.comworker.model.RegDocument;
import nabi.comworker.registration.repository.ApplicationNumberRepository;
import nabi.comworker.registration.repository.RegDocumentRepository;
import nabi.comworker.service.ApplicationNumberService;

@Service
public class ApplicationNumberServiceImpl implements ApplicationNumberService {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationNumberServiceImpl.class);

	@Autowired
	private ApplicationNumberRepository applicationNumberRepository;

	@Autowired
	private RegDocumentRepository regDocumentRepository;

	@Autowired
	private EmailService emailService;

	@Override
	public ApplicationNumber generateApplicationNumber(Long documentId) {
		// Fetch the document by ID
		RegDocument regDocument = regDocumentRepository.findById(documentId)
				.orElseThrow(() -> new IllegalArgumentException(
						"Application number cannot be generated. Document with ID " + documentId + " not found."));

		// Check if an application number already exists for this documentId
		List<ApplicationNumber> existingApplicationNumbers = applicationNumberRepository.findByDocumentId(documentId);

		if (existingApplicationNumbers.size() > 1) {
			throw new IllegalStateException(
					"Multiple application numbers found for the same documentId. Data integrity issue.");
		}

		ApplicationNumber existingApplicationNumber = existingApplicationNumbers.isEmpty() ? null
				: existingApplicationNumbers.get(0);

		if (existingApplicationNumber != null) {
			LocalDateTime now = LocalDateTime.now();
			long daysDifference = ChronoUnit.DAYS.between(existingApplicationNumber.getCreatedDate(), now);

			if (daysDifference <= 90) {
				throw new IllegalStateException("Your application has already been submitted in the last 90 days.");
			}
		}

		// Generate a new application number
		LocalDateTime now = LocalDateTime.now();
		String formattedDate = now.format(DateTimeFormatter.ofPattern("ddMMyyHHmmssSSS"));

		Random random = new Random();
		int insertIndex = random.nextInt(7); // 0–6

		String applicationNo = formattedDate.substring(0, insertIndex) + ApplicationNumberPrefix.PREFIX
				+ formattedDate.substring(insertIndex);

		ApplicationNumber appNumber = new ApplicationNumber(applicationNo, regDocument.getId(), now, now);

		// Save first
		ApplicationNumber savedAppNumber = applicationNumberRepository.save(appNumber);

		// Fetch employee details for sending email
		List<Object[]> employeeByApplicationNo = applicationNumberRepository.findEmployeeByApplicationNo(applicationNo);
		String to = null;
		String userName = null;

		if (employeeByApplicationNo != null && !employeeByApplicationNo.isEmpty()) {
			Object[] row = employeeByApplicationNo.get(0);
			if (row[0] != null)
				userName = row[0].toString();
			if (row[1] != null)
				to = row[1].toString();
		}

		System.out.println("Candidate Name: " + userName);
		System.out.println("Email: " + to);

		// Send email asynchronously (after saving)
		if (to != null && !to.isEmpty()) {
			final String finalTo = to;
			final String finalUserName = userName;

			CompletableFuture.runAsync(() -> {
				try {
					emailService.sendHtmlEmail(finalTo, finalUserName, applicationNo);
				} catch (Exception e) {
					logger.error("Failed to send email to {}", finalTo, e);
				}
			});
		}

		return savedAppNumber;
	}

}
