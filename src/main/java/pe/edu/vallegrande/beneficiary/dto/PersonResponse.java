package pe.edu.vallegrande.beneficiary.dto;

import java.util.List;

public class PersonResponse {
    private List<PersonDTO> persons; // Cambiado a una lista de PersonDTO
    private List<SavedPersonDTO> savedPersons; // Lista de SavedPersonDTO

    public PersonResponse(List<PersonDTO> persons) {
        this.persons = persons;
    }

    // Constructor que acepta ambas listas
    public PersonResponse(List<PersonDTO> persons, List<SavedPersonDTO> savedPersons) {
        this.persons = persons;
        this.savedPersons = savedPersons;
    }
    // Getters y Setters
    public List<PersonDTO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonDTO> persons) {
        this.persons = persons;
    }

    public List<SavedPersonDTO> getSavedPersons() {
        return savedPersons;
    }

    public void setSavedPersons(List<SavedPersonDTO> savedPersons) {
        this.savedPersons = savedPersons;
    }
}
