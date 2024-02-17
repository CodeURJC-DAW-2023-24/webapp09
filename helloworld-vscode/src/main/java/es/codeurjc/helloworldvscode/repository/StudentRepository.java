package es.codeurjc.helloworldvscode.repository;

import es.codeurjc.helloworldvscode.Entitys.Student;
import org.springframework.data.jpa.repository.JpaRepository;




public interface StudentRepository extends JpaRepository<Student, Long>{

}