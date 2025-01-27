package pe.edu.vallegrande.beneficiary.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;

@Data // Esta anotación genera automáticamente los getters, setters, toString, equals y hashCode
@Table("person") // Nombre de la tabla en la base de datos
public class Person {

    @Id
    @Column("id_person") // Nombre de la columna en la base de datos
    private Integer idPerson;

    @Column("name") // Nombre de la columna en la base de datos
    private String name;

    @Column("surname") // Nombre de la columna en la base de datos
    private String surname;

    @Column("age") // Nombre de la columna en la base de datos
    private Integer age;

    @Column("birthdate") // Nombre de la columna en la base de datos
    private LocalDate birthdate;

    @Column("type_document") // Nombre de la columna en la base de datos
    private String typeDocument;

    @Column("document_number") // Nombre de la columna en la base de datos
    private String documentNumber;

    @Column("type_kinship") // Nombre de la columna en la base de datos
    private String typeKinship;

    @Column("sponsored") // Nombre de la columna en la base de datos
    private String sponsored;

    @Column("state") // Nombre de la columna en la base de datos
    private String state;

    @Column("family_id_family") // Nombre de la columna en la base de datos
    private Integer familyId;

}
