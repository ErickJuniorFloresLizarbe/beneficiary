package pe.edu.vallegrande.beneficiary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.beneficiary.dto.EducationDTO;
import pe.edu.vallegrande.beneficiary.dto.HealthDTO;
import pe.edu.vallegrande.beneficiary.dto.PersonDTO;
import pe.edu.vallegrande.beneficiary.model.Education;
import pe.edu.vallegrande.beneficiary.model.Health;
import pe.edu.vallegrande.beneficiary.repository.EducationRepository;
import pe.edu.vallegrande.beneficiary.repository.HealthRepository;
import pe.edu.vallegrande.beneficiary.repository.PersonRepository;
import reactor.core.publisher.Mono;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private HealthRepository healthRepository;

    // Método privado para mapear Education a EducationDTO
    private EducationDTO mapToEducationDTO(Education education) {
        EducationDTO educationDTO = new EducationDTO();
        educationDTO.setIdEducation(education.getIdEducation());
        educationDTO.setDegreeStudy(education.getDegreeStudy());
        educationDTO.setGradeBook(education.getGradeBook());
        educationDTO.setGradeAverage(education.getGradeAverage());
        educationDTO.setFullNotebook(education.getFullNotebook());
        educationDTO.setAssistance(education.getAssistance());
        educationDTO.setTutorials(education.getTutorials());
        educationDTO.setPersonId(education.getPersonId());
        return educationDTO;
    }

    // Método privado para mapear Health a HealthDTO
    private HealthDTO mapToHealthDTO(Health health) {
        HealthDTO healthDTO = new HealthDTO();
        healthDTO.setIdHealth(health.getIdHealth());
        healthDTO.setVaccine(health.getVaccine());
        healthDTO.setVph(health.getVph());
        healthDTO.setInfluenza(health.getInfluenza());
        healthDTO.setDeworming(health.getDeworming());
        healthDTO.setHemoglobin(health.getHemoglobin());
        healthDTO.setPersonId(health.getPersonId());
        return healthDTO;
    }

    public Mono<PersonDTO> getPersonWithDetails(Long idPerson) { // Cambiado a Long
        return personRepository.findByIdPerson(idPerson)
                .flatMap(person ->
                        Mono.zip(
                                educationRepository.findByPersonId(idPerson),
                                healthRepository.findByPersonId(idPerson),
                                (education, health) -> {
                                    // Crear y llenar el objeto PersonDTO
                                    PersonDTO personDTO = new PersonDTO();
                                    personDTO.setIdPerson(person.getIdPerson());
                                    personDTO.setName(person.getName());
                                    personDTO.setSurname(person.getSurname());
                                    personDTO.setAge(person.getAge());
                                    personDTO.setBirthdate(person.getBirthdate());
                                    personDTO.setTypeDocument(person.getTypeDocument());
                                    personDTO.setDocumentNumber(person.getDocumentNumber());
                                    personDTO.setTypeKinship(person.getTypeKinship());
                                    personDTO.setSponsored(person.getSponsored());
                                    personDTO.setState(person.getState());
                                    personDTO.setFamilyId(person.getFamilyId());

                                    // Usar el método mapToEducationDTO para crear el EducationDTO
                                    EducationDTO educationDTO = mapToEducationDTO(education);

                                    // Usar el método mapToHealthDTO para crear el HealthDTO
                                    HealthDTO healthDTO = mapToHealthDTO(health);

                                    // Asignar los DTOs a PersonDTO
                                    personDTO.setEducation(educationDTO);
                                    personDTO.setHealth(healthDTO);

                                    return personDTO;
                                }
                        ));
    }
}
