package es.codeurjc.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.codeurjc.backend.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findById(long id);
    
    List<Subject> findByGender(String gender);

    @SuppressWarnings("null")
    Page<Subject> findAll(Pageable page);

    Optional<Subject> findByName(String name);

    @Query("SELECT s.gender, COUNT(s) FROM Subject s GROUP BY s.gender")
    List<Object[]> countSubjectsByGender();

    Page<Subject> findAllByIdNotIn(List<Long> enrolledSubjects, Pageable page);

}
