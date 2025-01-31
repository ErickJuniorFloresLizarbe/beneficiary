package pe.edu.vallegrande.beneficiary.dto;

import java.util.List;
import lombok.Data;

@Data
public class PersonRequest {
    private List<PersonDTO> persons; // Lista de PersonDTO
}
