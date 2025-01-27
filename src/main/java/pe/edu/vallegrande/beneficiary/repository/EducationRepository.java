package pe.edu.vallegrande.beneficiary.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.beneficiary.model.Education;
import reactor.core.publisher.Flux;

public interface EducationRepository extends ReactiveCrudRepository<Education, Integer> {
    Flux<Education> findByPersonId(Integer idPerson);
}

