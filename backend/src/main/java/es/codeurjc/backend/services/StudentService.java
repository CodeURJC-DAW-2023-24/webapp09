
package es.codeurjc.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.backend.model.Student;
import es.codeurjc.backend.model.Subject;
import es.codeurjc.backend.repository.StudentRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public boolean emailRepeat(String email){

        List<Student> students = studentRepository.findAll();
        
        for(Student student: students){
            if(student.getEmail().equals(email)){
                return false;
            }
        }
        return true;

    }

    public List<Subject> findSubjectsByStudentName(String name) {
        Optional<Student> student = studentRepository.findByFirstName(name);

        if (student.isPresent()) {
            return new ArrayList<>(student.get().getSubjects());
        } else {
            return new ArrayList<>();
        }
    }

    public List<Subject> findSubjectsByStudentEmail(String email) {
        Optional<Student> student = studentRepository.findByEmail(email);

        if (student.isPresent()) {
            return new ArrayList<>(student.get().getSubjects());
        } else {
            return new ArrayList<>();
        }
    }

    public List<Subject> findSubjectsByStudentId(Long studentId) {
        @SuppressWarnings("null")
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            return new ArrayList<>(student.get().getSubjects());
        } else {
            return new ArrayList<>();
        }
    }

    

    public Student getStudentByName(String name) {
        // Retorna el estudiante o lanza una excepción si no se encuentra
        return studentRepository.findByFirstName(name)
                .orElseThrow(() -> new RuntimeException("Student not found with name " + name));
    }

    public Student getStudentByEmail(String email) {
        // Retorna el estudiante o lanza una excepción si no se encuentra
        return studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found with name " + email));
    }

    @SuppressWarnings("null")
    public Student getStudentById(Long studentId) {
        // Retorna el estudiante o lanza una excepción si no se encuentra
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + studentId));
    }

    public boolean existsByEmail(List<Student> students, String email) {
        return students.stream()
                .anyMatch(student -> email.equals(student.getEmail()));
    }
}