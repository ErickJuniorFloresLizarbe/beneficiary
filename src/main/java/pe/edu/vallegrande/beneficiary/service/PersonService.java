package pe.edu.vallegrande.beneficiary.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.beneficiary.dto.EducationDTO;
import pe.edu.vallegrande.beneficiary.dto.HealthDTO;
import pe.edu.vallegrande.beneficiary.dto.PersonDTO;
import pe.edu.vallegrande.beneficiary.model.Education;
import pe.edu.vallegrande.beneficiary.model.Health;
import pe.edu.vallegrande.beneficiary.model.Person;
import pe.edu.vallegrande.beneficiary.repository.EducationRepository;
import pe.edu.vallegrande.beneficiary.repository.HealthRepository;
import pe.edu.vallegrande.beneficiary.repository.PersonRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private HealthRepository healthRepository;

    // Método privado para mapear Person a Person
    private PersonDTO mapToPersonDTO(Person person) {
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
    return personDTO;
}

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

    public Flux<PersonDTO> getPersonWithDetails(Integer idPerson) {
        return personRepository.findByIdPerson(idPerson)
                .flatMap(person -> {
                    return Mono.zip(
                            educationRepository.findByPersonId(person.getIdPerson()) // Cambiado a getIdPerson()
                                    .collectList()
                                    .defaultIfEmpty(Collections.emptyList()),
                            healthRepository.findByPersonId(person.getIdPerson()) // Cambiado a getIdPerson()
                                    .collectList()
                                    .defaultIfEmpty(Collections.emptyList()),
                            (educations, healths) -> {
                                // Mapeo a PersonDTO
                                PersonDTO personDTO = mapToPersonDTO(person);
    
                                // Asignar detalles de educación y salud
                                personDTO.setEducation(educations.stream()
                                        .map(this::mapToEducationDTO)
                                        .collect(Collectors.toList()));
                                personDTO.setHealth(healths.stream()
                                        .map(this::mapToHealthDTO)
                                        .collect(Collectors.toList()));
    
                                return personDTO;
                            }
                    );
                })
                .switchIfEmpty(Flux.error(new RuntimeException("No se encontraron personas con el ID proporcionado")));
    }
    
    
    
    
    
    
    
    

    public Flux<PersonDTO> getAllPersons() {
        return personRepository.findAll()
                .flatMap(person -> Mono.zip(
                        educationRepository.findByPersonId(person.getIdPerson())
                                .collectList() // Ahora esto funcionará porque devuelve un Flux
                                .defaultIfEmpty(Collections.emptyList()), // Maneja el caso vacío
                        healthRepository.findByPersonId(person.getIdPerson())
                                .collectList() // Ahora esto funcionará porque devuelve un Flux
                                .defaultIfEmpty(Collections.emptyList()), // Maneja el caso vacío
                        (educations, healths) -> {
                            PersonDTO personDTO = mapToPersonDTO(person); // Usa el método de mapeo
    
                            // Mapea la lista de educaciones
                            List<EducationDTO> educationDTOs = educations.stream()
                                    .map(this::mapToEducationDTO)
                                    .collect(Collectors.toList());
                            personDTO.setEducation(educationDTOs);
    
                            // Mapea la lista de salud
                            List<HealthDTO> healthDTOs = healths.stream()
                                    .map(this::mapToHealthDTO)
                                    .collect(Collectors.toList());
                            personDTO.setHealth(healthDTOs);
    
                            return personDTO;
                        }
                ))
                .switchIfEmpty(Flux.error(new RuntimeException("No se encontraron personas")));
    }
    
    

    
    
    
}
