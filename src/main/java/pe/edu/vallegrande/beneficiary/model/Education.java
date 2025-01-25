package pe.edu.vallegrande.beneficiary.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Table("education")
public class Education {
    @Id
    private Long idEducation;
    private String degreeStudy;
    private String gradeBook;
    private int gradeAverage;
    private String fullNotebook;
    private String assistance;
    private String tutorials;
    private Integer personId;
}
