package pe.edu.vallegrande.beneficiary.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import pe.edu.vallegrande.beneficiary.model.Education;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EducationRepository extends R2dbcRepository<Education, Integer> {
    Flux<Education> findByPersonId(Integer personId);

    //INSERTA NUEVO REGISTRO CUANDO SE EDITA
    @Modifying
    @Query("INSERT INTO education (degree_study, grade_book, grade_average, full_notebook, educational_assistance, academic_tutorials, person_id_person) " +
           "VALUES (:degreeStudy, :gradeBook, :gradeAverage, :fullNotebook, :assistance, :tutorials, :personId)")
    Mono<Integer> insertEducation(String degreeStudy, String gradeBook, int gradeAverage, String fullNotebook, String assistance, String tutorials, Integer personId);

    
}