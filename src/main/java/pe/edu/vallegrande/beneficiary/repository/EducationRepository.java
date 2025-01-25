package pe.edu.vallegrande.beneficiary.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.beneficiary.model.Education;
import reactor.core.publisher.Mono;

public interface EducationRepository extends ReactiveCrudRepository<Education, Long> {
    Mono<Education> findByPersonId(Long personId);
}

