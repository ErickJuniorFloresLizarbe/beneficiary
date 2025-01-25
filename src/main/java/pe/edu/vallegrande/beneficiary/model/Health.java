package pe.edu.vallegrande.beneficiary.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Table("health")
public class Health {
    @Id
    private Long idHealth;
    private String vaccine;
    private String vph;
    private String influenza;
    private String deworming;
    private String hemoglobin;
    private Integer personId;
}
