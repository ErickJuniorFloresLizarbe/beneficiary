package pe.edu.vallegrande.beneficiary.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Table("person")
public class Person {
    @Id
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
}
