package es.codeurjc.helloworldvscode.repository;

import es.codeurjc.helloworldvscode.Entitys.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.ArrayList;
import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student, Long>{

    Optional<Student> findByFirstName(String firstName);
    Optional<Student> findByEmail(String email);
    Optional<Student> findStudentByLastNameAndFirstName(String firstName, String lastName);
    
    ArrayList<Student> findAllBySubjectsId(Long subjectId);

}
