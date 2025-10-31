package nabi.comworker.registration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import nabi.comworker.model.ApplicationNumber;

public interface ApplicationNumberRepository extends JpaRepository<ApplicationNumber, Long> {
	
	 List<ApplicationNumber> findByDocumentId(Long documentId);
	 
		@Query(value = "SELECT gem.candidate_name, gem.email " + "FROM gm_employees_master gem "
				+ "LEFT JOIN addresses a ON gem.id = a.employee_id "
				+ "LEFT JOIN reg_documents rd ON rd.address_id = a.id "
				+ "LEFT JOIN application_numbers apno ON rd.id = apno.document_id "
				+ "WHERE apno.application_no = :applicationNo", nativeQuery = true)
		List<Object[]> findEmployeeByApplicationNo(@Param("applicationNo") String applicationNo);

}
