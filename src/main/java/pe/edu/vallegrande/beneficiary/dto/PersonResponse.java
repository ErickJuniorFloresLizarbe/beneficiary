package pe.edu.vallegrande.beneficiary.dto;

import java.util.List;

public class PersonResponse {
    private List<PersonDTO> persons; // Cambiado a una lista de PersonDTO

    // Constructor
    public PersonResponse(List<PersonDTO> persons) {
        this.persons = persons;
    }

    // Getters y Setters
    public List<PersonDTO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonDTO> persons) {
        this.persons = persons;
    }
}
