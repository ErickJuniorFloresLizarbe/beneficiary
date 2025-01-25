package pe.edu.vallegrande.beneficiary.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.beneficiary.model.Person;
import reactor.core.publisher.Mono;

public interface PersonRepository extends ReactiveCrudRepository<Person, Long> {
    Mono<Person> findByIdPerson(Long idPerson);
}
