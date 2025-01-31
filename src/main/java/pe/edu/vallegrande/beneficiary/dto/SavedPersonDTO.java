package pe.edu.vallegrande.beneficiary.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class SavedPersonDTO {
    private Integer idPerson;
    private String name;
    private String surname;
    private Integer age;
    private LocalDate birthdate;
    private String typeDocument;
    private String documentNumber;
    private String typeKinship;
    private String sponsored;
    private String state;
    private Integer familyId;
    private List<EducationDTO> education;
    private List<HealthDTO> health;

    // Constructor
    public SavedPersonDTO(Integer idPerson, String name, String surname, Integer age, LocalDate birthdate,
                          String typeDocument, String documentNumber, String typeKinship,
                          String sponsored, String state, Integer familyId,
                          List<EducationDTO> education, List<HealthDTO> health) {
        this.idPerson = idPerson;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.birthdate = birthdate;
        this.typeDocument = typeDocument;
        this.documentNumber = documentNumber;
        this.typeKinship = typeKinship;
        this.sponsored = sponsored;
        this.state = state;
        this.familyId = familyId;
        this.education = education;
        this.health = health;
    }
}
