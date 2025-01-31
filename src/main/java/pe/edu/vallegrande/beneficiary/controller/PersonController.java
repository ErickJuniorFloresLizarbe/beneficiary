package pe.edu.vallegrande.beneficiary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pe.edu.vallegrande.beneficiary.dto.PersonRequest;
import pe.edu.vallegrande.beneficiary.dto.PersonResponse;
import pe.edu.vallegrande.beneficiary.service.PersonService;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
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
    
    @PostMapping("/register")
    public Mono<PersonResponse> registerPersons(@RequestBody PersonRequest personRequest) {
        return personService.registerPersons(personRequest);
    }

    @PutMapping("/persons/{id}")
    public Mono<PersonResponse> updatePerson(@PathVariable Integer id, @RequestBody PersonRequest personRequest) {
        return personService.updatePerson(id, personRequest);
    }

}
