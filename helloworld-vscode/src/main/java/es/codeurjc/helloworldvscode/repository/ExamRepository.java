package es.codeurjc.helloworldvscode.repository;

import es.codeurjc.helloworldvscode.Entitys.Exam;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;




public interface ExamRepository extends JpaRepository<Exam, Long>{
    ArrayList<Exam> findAllBySubjectId(Long subjectId);
    Optional<Exam> findById(String examId);
    Optional<Exam> findBySubjectIdAndExamId(Long subjectId, Long examId);
}