package pe.edu.vallegrande.beneficiary.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import pe.edu.vallegrande.beneficiary.model.Health;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HealthRepository extends R2dbcRepository<Health, Integer> {
    Flux<Health> findByPersonId(Integer personId);
    
    //INSERTA NUEVO REGISTRO CUANDO SE EDITA
    @Modifying
    @Query("INSERT INTO health (vaccine_schemes, vph, influenza, deworming, hemoglobin, person_id_person) " +
           "VALUES (:vaccine, :vph, :influenza, :deworming, :hemoglobin, :personId)")
    Mono<Integer> insertHealth(String vaccine, String vph, String influenza, String deworming, String hemoglobin, Integer personId);


}