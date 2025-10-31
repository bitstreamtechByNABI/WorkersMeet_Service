package nabi.comworker.service;

import nabi.comworker.model.ApplicationNumber;

public interface ApplicationNumberService {
	
	 ApplicationNumber generateApplicationNumber(Long documentId);

}
