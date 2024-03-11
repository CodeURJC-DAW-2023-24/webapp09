package es.codeurjc.backend.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.backend.Entitys.ExamStudent;




public interface ExamStudentRepository extends JpaRepository<ExamStudent, Long>{
    ArrayList<ExamStudent> findByExamId(Long examId);
    ArrayList<ExamStudent> findAllByExamId(Long subjectId);
    ArrayList<ExamStudent> findAllByStudentId(Long id);
    ExamStudent findByStudentIdAndExamId(Long id, Long id2);
}