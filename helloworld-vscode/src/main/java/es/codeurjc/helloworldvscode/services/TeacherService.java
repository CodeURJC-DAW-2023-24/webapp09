package es.codeurjc.helloworldvscode.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.helloworldvscode.Entitys.Student;
import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.Entitys.Teacher;
import es.codeurjc.helloworldvscode.repository.StudentRepository;
import es.codeurjc.helloworldvscode.repository.TeacherRepository;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    public List<Subject> findSubjectsByTeacherId(Long teacherId) {
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        if (teacher.isPresent()) {
            return new ArrayList<>(teacher.get().getSubjects());
        } else {
            return new ArrayList<>();
        }
    }

    public Teacher getStudentById(Long teacherId) {
        // Retorna el estudiante o lanza una excepción si no se encuentra
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + teacherId));
    }
}
