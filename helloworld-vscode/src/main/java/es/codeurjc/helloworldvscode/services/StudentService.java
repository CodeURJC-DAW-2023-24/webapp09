
package es.codeurjc.helloworldvscode.Services;

import es.codeurjc.helloworldvscode.Entitys.Student;
import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Subject> findSubjectsByStudentId(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            return new ArrayList<>(student.get().getSubjects());
        } else {
            return new ArrayList<>();
        }
    }

    public Student getStudentById(Long studentId) {
        // Retorna el estudiante o lanza una excepción si no se encuentra
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + studentId));
    }
}