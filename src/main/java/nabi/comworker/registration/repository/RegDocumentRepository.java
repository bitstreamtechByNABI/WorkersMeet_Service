package nabi.comworker.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nabi.comworker.model.RegDocument;

public interface RegDocumentRepository extends JpaRepository<RegDocument, Long> {

}
