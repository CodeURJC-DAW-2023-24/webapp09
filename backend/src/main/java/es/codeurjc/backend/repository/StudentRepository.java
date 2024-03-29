package es.codeurjc.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.backend.model.Student;

import java.util.ArrayList;
import java.util.Optional;



public interface StudentRepository extends JpaRepository<Student, Long>{
    Optional<Student> findByFirstName(String name);
    // REVISAR
    Optional<Student> findByEmail(String email);
    Optional<Student> findStudentByLastNameAndFirstName(String firstName, String lastName);
    ArrayList<Student> findAllBySubjectsId(Long subjectId);
    boolean existsByEmail(String email);
}
