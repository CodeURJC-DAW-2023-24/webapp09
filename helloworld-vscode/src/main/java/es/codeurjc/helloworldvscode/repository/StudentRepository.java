package es.codeurjc.helloworldvscode.repository;

import es.codeurjc.helloworldvscode.Entitys.Student;
import es.codeurjc.helloworldvscode.Entitys.User;
import es.codeurjc.helloworldvscode.Entitys.ExamStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;


public interface StudentRepository extends JpaRepository<Student, Long>{
    Optional<Student> findByFirstName(String name);

}
