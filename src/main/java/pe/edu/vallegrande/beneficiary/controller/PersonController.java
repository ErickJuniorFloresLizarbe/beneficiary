package pe.edu.vallegrande.beneficiary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.beneficiary.dto.PersonResponse;
import pe.edu.vallegrande.beneficiary.service.PersonService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonService personService;
    
    @GetMapping("/{id}")
    public Mono<PersonResponse> getPersonDetails(@PathVariable Integer id) {
        return personService.getPersonWithDetails(id)
                .collectList() // Recoge todos los PersonDTO en una lista
                .map(personDTOs -> new PersonResponse(personDTOs)); // Crea un PersonResponse con la lista
    }



    @GetMapping("/persons")
    public Mono<PersonResponse> getAllPersons() {
        return personService.getAllPersons()
                .collectList() // Recoge todos los PersonDTO en una lista
                .map(personDTOs -> new PersonResponse(personDTOs)); // Crea un PersonResponse con la lista
    }

}
