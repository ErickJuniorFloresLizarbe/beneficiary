package pe.edu.vallegrande.beneficiary.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PersonDTO {
    private Long idPerson;
    private String name;
    private String surname;
    private int age;
    private String birthdate;
    private String typeDocument;
    private String documentNumber;
    private String typeKinship;
    private String sponsored;
    private String state;
    private Integer familyId;
    private EducationDTO education;
    private HealthDTO health;
}

