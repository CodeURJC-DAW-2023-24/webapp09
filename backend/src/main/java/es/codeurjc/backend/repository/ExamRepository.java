package es.codeurjc.backend.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.backend.model.Exam;




public interface ExamRepository extends JpaRepository<Exam, Long>{
    ArrayList<Exam> findAllBySubjectId(Long subjectId);
    Optional<Exam> findById(String examId);
    @SuppressWarnings("null")
    Optional<Exam> findById(Long examId);
    Optional<Exam> findBySubjectIdAndExamId(Long subjectId, Long examId);

    Page<Exam> findAllBysubjectId(Long subjectId, PageRequest page);
    
}