package pe.edu.vallegrande.beneficiary.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class PersonDTO {
    private Integer idPerson;
    private String name;
    private String surname;
    private Integer age;
    private LocalDate  birthdate;
    private String typeDocument;
    private String documentNumber;
    private String typeKinship;
    private String sponsored;
    private String state;
    private Integer familyId;
    private List<EducationDTO> education; // Cambiado a List<EducationDTO>
    private List<HealthDTO> health;
}