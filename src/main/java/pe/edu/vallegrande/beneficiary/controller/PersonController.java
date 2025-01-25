package pe.edu.vallegrande.beneficiary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.beneficiary.dto.PersonDTO;
import pe.edu.vallegrande.beneficiary.service.PersonService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/{id}")
    public Mono<PersonDTO> getPersonDetails(@PathVariable Long id) {
        return personService.getPersonWithDetails(id);
    }
}
