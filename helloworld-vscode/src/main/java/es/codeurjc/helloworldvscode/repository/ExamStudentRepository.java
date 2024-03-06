package es.codeurjc.helloworldvscode.repository;

import es.codeurjc.helloworldvscode.Entitys.Exam;
import es.codeurjc.helloworldvscode.Entitys.ExamStudent;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;




public interface ExamStudentRepository extends JpaRepository<ExamStudent, Long>{
    ArrayList<ExamStudent> findByExamId(Long examId);
    ArrayList<ExamStudent> findAllByExamId(Long subjectId);
    ArrayList<ExamStudent> findAllByStudentId(Long id);
}