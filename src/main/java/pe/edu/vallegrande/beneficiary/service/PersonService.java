package pe.edu.vallegrande.beneficiary.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.beneficiary.dto.EducationDTO;
import pe.edu.vallegrande.beneficiary.dto.HealthDTO;
import pe.edu.vallegrande.beneficiary.dto.PersonDTO;
import pe.edu.vallegrande.beneficiary.dto.PersonRequest;
import pe.edu.vallegrande.beneficiary.dto.PersonResponse;
import pe.edu.vallegrande.beneficiary.dto.SavedPersonDTO;
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

    //LISTADO ID
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
    
    //LISTADO ALL
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




    //METODO PARA CREAR NUEVO REGISTRO
    private Person convertToEntity(PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getName());
        person.setSurname(personDTO.getSurname());
        person.setAge(personDTO.getAge());
        person.setBirthdate(personDTO.getBirthdate());
        person.setTypeDocument(personDTO.getTypeDocument());
        person.setDocumentNumber(personDTO.getDocumentNumber());
        person.setTypeKinship(personDTO.getTypeKinship());
        person.setSponsored(personDTO.getSponsored());
        person.setState(personDTO.getState());
        person.setFamilyId(personDTO.getFamilyId());
        return person;
    }


    private Mono<Void> saveEducationAndHealth(PersonDTO personDTO, Integer personId) {
        Flux<Education> educationFlux = Flux.fromIterable(personDTO.getEducation())
                .map(educationDTO -> {
                    Education education = new Education();
                    education.setDegreeStudy(educationDTO.getDegreeStudy());
                    education.setGradeBook(educationDTO.getGradeBook());
                    education.setGradeAverage(educationDTO.getGradeAverage());
                    education.setFullNotebook(educationDTO.getFullNotebook());
                    education.setAssistance(educationDTO.getAssistance());
                    education.setTutorials(educationDTO.getTutorials());
                    education.setPersonId(personId);
                    return education;
                });
    
        Flux<Health> healthFlux = Flux.fromIterable(personDTO.getHealth())
                .map(healthDTO -> {
                    Health health = new Health();
                    health.setVaccine(healthDTO.getVaccine());
                    health.setVph(healthDTO.getVph());
                    health.setInfluenza(healthDTO.getInfluenza());
                    health.setDeworming(healthDTO.getDeworming());
                    health.setHemoglobin(healthDTO.getHemoglobin());
                    health.setPersonId(personId);
                    return health;
                });
    
        return Flux.concat(educationRepository.saveAll(educationFlux), healthRepository.saveAll(healthFlux)).then();
    }
    
    
        public Mono<PersonResponse> registerPersons(PersonRequest personRequest) {
    List<PersonDTO> persons = personRequest.getPersons();

    // Procesar cada persona
    return Flux.fromIterable(persons)
            .flatMap(personDTO -> {
                Person person = convertToEntity(personDTO); // Convierte PersonDTO a Person

                return personRepository.save(person)
                        .flatMap(savedPerson -> {
                            // Aquí definimos explícitamente el tipo de retorno
                            return Mono.zip(
                                    saveEducationAndHealth(personDTO, savedPerson.getIdPerson()),
                                    Mono.just(savedPerson) // Retorna el savedPerson
                            ).map(tuple -> {
                                // Aquí puedes crear el SavedPersonDTO
                                return new SavedPersonDTO(
                                        savedPerson.getIdPerson(),
                                        savedPerson.getName(),
                                        savedPerson.getSurname(),
                                        savedPerson.getAge(),
                                        savedPerson.getBirthdate(),
                                        savedPerson.getTypeDocument(),
                                        savedPerson.getDocumentNumber(),
                                        savedPerson.getTypeKinship(),
                                        savedPerson.getSponsored(),
                                        savedPerson.getState(),
                                        savedPerson.getFamilyId(),
                                        personDTO.getEducation(),
                                        personDTO.getHealth()
                                );
                            });
                        });
            })
            .collectList() // Recoge todos los SavedPersonDTO en una lista
            // En tu servicio
        .map(savedPersons -> new PersonResponse(null, savedPersons)); // Pasando null para la lista de PersonDTO

}


        //EDITAR 
        public Mono<PersonResponse> updatePerson(Integer idPerson, PersonRequest personRequest) {
                PersonDTO updatedPersonDTO = personRequest.getPersons().get(0); // Obtener el primer elemento de la lista
        
                return personRepository.findByIdPerson(idPerson)
                        .next() // Obtiene el primer elemento del Flux como un Mono
                        .flatMap(existingPerson -> {
                        // Actualiza los campos de la persona
                        existingPerson.setName(updatedPersonDTO.getName());
                        existingPerson.setSurname(updatedPersonDTO.getSurname());
                        existingPerson.setAge(updatedPersonDTO.getAge());
                        existingPerson.setBirthdate(updatedPersonDTO.getBirthdate());
                        existingPerson.setTypeDocument(updatedPersonDTO.getTypeDocument());
                        existingPerson.setDocumentNumber(updatedPersonDTO.getDocumentNumber());
                        existingPerson.setTypeKinship(updatedPersonDTO.getTypeKinship());
                        existingPerson.setSponsored(updatedPersonDTO.getSponsored());
                        existingPerson.setState(updatedPersonDTO.getState());
                        existingPerson.setFamilyId(updatedPersonDTO.getFamilyId());
        
                        // Guarda la persona actualizada
                        return personRepository.save(existingPerson)
                                .flatMap(savedPerson -> {
                                        // Actualiza la educación y salud
                                        return updateEducationAndHealth(savedPerson.getIdPerson(), updatedPersonDTO)
                                                .then(Mono.just(savedPerson)); // Asegúrate de que esto sea Mono.just(savedPerson)
                                });
                        })
                        .map(savedPerson -> new PersonResponse(Collections.singletonList(mapToPersonDTO(savedPerson)), null)); // Retorna el PersonResponse
        }
    
    

        private Mono<Void> updateEducationAndHealth(Integer personId, PersonDTO updatedPersonDTO) {
                // Actualizar Educación
                Flux<Education> educationFlux = Flux.fromIterable(updatedPersonDTO.getEducation())
                        .flatMap(educationDTO -> {
                        Education newEducation = new Education();
                        newEducation.setDegreeStudy(educationDTO.getDegreeStudy());
                        newEducation.setGradeBook(educationDTO.getGradeBook());
                        newEducation.setGradeAverage(educationDTO.getGradeAverage());
                        newEducation.setFullNotebook(educationDTO.getFullNotebook());
                        newEducation.setAssistance(educationDTO.getAssistance());
                        newEducation.setTutorials(educationDTO.getTutorials());
                        newEducation.setPersonId(personId);
                        return educationRepository.save(newEducation);
                        });
        
                // Actualizar Salud
                Flux<Health> healthFlux = Flux.fromIterable(updatedPersonDTO.getHealth())
                        .flatMap(healthDTO -> {
                        Health newHealth = new Health();
                        newHealth.setVaccine(healthDTO.getVaccine());
                        newHealth.setVph(healthDTO.getVph());
                        newHealth.setInfluenza(healthDTO.getInfluenza());
                        newHealth.setDeworming(healthDTO.getDeworming());
                        newHealth.setHemoglobin(healthDTO.getHemoglobin());
                        newHealth.setPersonId(personId);
                        return healthRepository.save(newHealth);
                        });
        
                return Flux.concat(educationFlux, healthFlux).then(); // Combina ambas operaciones y devuelve Mono<Void>
        }
    
            
    

        

    
    
}
