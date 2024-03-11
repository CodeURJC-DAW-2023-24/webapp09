package es.codeurjc.backend.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.backend.Entitys.Exam;




public interface ExamRepository extends JpaRepository<Exam, Long>{
    ArrayList<Exam> findAllBySubjectId(Long subjectId);
    Optional<Exam> findById(String examId);
    @SuppressWarnings("null")
    Optional<Exam> findById(Long examId);
    Optional<Exam> findBySubjectIdAndExamId(Long subjectId, Long examId);
}