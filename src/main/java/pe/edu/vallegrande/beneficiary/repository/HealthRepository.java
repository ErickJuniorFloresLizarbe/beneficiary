package pe.edu.vallegrande.beneficiary.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.beneficiary.model.Health;
import reactor.core.publisher.Mono;

public interface HealthRepository extends ReactiveCrudRepository<Health, Long> {
    Mono<Health> findByPersonId(Long personId);
}
