package es.codeurjc.helloworldvscode.services;

import es.codeurjc.helloworldvscode.Entitys.ExamStudent;
import es.codeurjc.helloworldvscode.Entitys.Student;
import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.repository.ExamRepository;
import es.codeurjc.helloworldvscode.repository.ExamStudentRepository;
import es.codeurjc.helloworldvscode.repository.StudentRepository;
import es.codeurjc.helloworldvscode.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {

    @Autowired
    private ExamStudentRepository examStudentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public List<ExamStudent> findExamStudentsByStudentAndSubject(Long studentId, Long subjectId) {
        // Obtener el estudiante y la asignatura por sus IDs
        Student student = studentRepository.findById(studentId).orElse(null);
        Subject subject = subjectRepository.findById(subjectId).orElse(null);

        if (student == null || subject == null) {
            return new ArrayList<>(); // Retorna una lista vacía si no se encuentra el estudiante o la asignatura
        }

        // Filtrar los ExamStudent por el estudiante y la asignatura especificados
        return examStudentRepository.findAll().stream()
                .filter(examStudent -> examStudent.getExam().getSubject().equals(subject) && examStudent.getStudent().equals(student))
                .collect(Collectors.toList());
    }
}
