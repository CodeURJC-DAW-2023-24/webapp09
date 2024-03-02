package es.codeurjc.helloworldvscode.services;

import es.codeurjc.helloworldvscode.Entitys.Exam;
import es.codeurjc.helloworldvscode.Entitys.ExamStudent;
import es.codeurjc.helloworldvscode.Entitys.Student;
import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.repository.ExamRepository;
import es.codeurjc.helloworldvscode.repository.ExamStudentRepository;
import es.codeurjc.helloworldvscode.repository.StudentRepository;
import es.codeurjc.helloworldvscode.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ExamService {

    @Autowired
    private ExamStudentRepository examStudentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private ExamStudentService examStudentService;

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

    public void store(MultipartFile file, Subject s, String nameExam) throws IOException {
        
        @SuppressWarnings("null")
        String type = file.getContentType().substring("application/".length());
        Exam exam = new Exam(nameExam, type, file.getBytes(), s);
        examRepository.save(exam);
        examStudentService.createExamsStudent(s, exam);
    }

    @SuppressWarnings("null")
    public Exam getFile(Long id) {
        return examRepository.findById(id).get();
    }

    public Stream<Exam> getAllFiles() {
        return examRepository.findAll().stream();
    }
}
