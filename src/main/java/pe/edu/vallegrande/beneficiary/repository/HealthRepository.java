package pe.edu.vallegrande.beneficiary.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.beneficiary.model.Health;
import reactor.core.publisher.Flux;

public interface HealthRepository extends ReactiveCrudRepository<Health, Integer> {
    Flux<Health> findByPersonId(Integer personId);
}
