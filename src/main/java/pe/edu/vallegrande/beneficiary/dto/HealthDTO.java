package pe.edu.vallegrande.beneficiary.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class HealthDTO {
    private Long idHealth;
    private String vaccine;
    private String vph;
    private String influenza;
    private String deworming;
    private String hemoglobin;
    private Integer personId;
}
