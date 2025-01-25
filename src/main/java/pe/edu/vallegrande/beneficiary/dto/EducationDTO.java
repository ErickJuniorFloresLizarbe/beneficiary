package pe.edu.vallegrande.beneficiary.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EducationDTO {
    private Long idEducation;
    private String degreeStudy;
    private String gradeBook;
    private int gradeAverage;
    private String fullNotebook;
    private String assistance;
    private String tutorials;
    private Integer personId;
}
