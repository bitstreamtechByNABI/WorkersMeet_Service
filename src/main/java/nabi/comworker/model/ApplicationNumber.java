package nabi.comworker.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "application_numbers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Custom application number
    @Column(nullable = false, unique = true)
    private String applicationNo;

    // Timestamps
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private LocalDateTime updatedDate;

    // Foreign Key: Document associated with this application number
    @ManyToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id", nullable = false)
    private RegDocument document;

    // Custom constructor for applicationNo, documentId, and timestamps
    public ApplicationNumber(String applicationNo, Long documentId, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.applicationNo = applicationNo;
        this.createdDate = createdDate != null ? createdDate : LocalDateTime.now();  // Use provided date or current date
        this.updatedDate = updatedDate != null ? updatedDate : LocalDateTime.now();  // Use provided date or current date
        this.document = new RegDocument();
        this.document.setId(documentId);  // Set the document ID
    }

    // Custom constructor for only applicationNo and documentId, automatically setting timestamps
    public ApplicationNumber(String applicationNo, Long documentId) {
        this(applicationNo, documentId, LocalDateTime.now(), LocalDateTime.now());
    }
}
