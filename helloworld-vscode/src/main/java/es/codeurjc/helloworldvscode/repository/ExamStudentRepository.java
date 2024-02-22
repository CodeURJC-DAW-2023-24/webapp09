package es.codeurjc.helloworldvscode.repository;

import es.codeurjc.helloworldvscode.Entitys.ExamStudent;
import org.springframework.data.jpa.repository.JpaRepository;




public interface ExamStudentRepository extends JpaRepository<ExamStudent, Long>{

}