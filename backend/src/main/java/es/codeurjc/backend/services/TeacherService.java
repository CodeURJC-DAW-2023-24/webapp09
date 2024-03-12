package es.codeurjc.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.backend.model.Subject;
import es.codeurjc.backend.model.Teacher;
import es.codeurjc.backend.repository.TeacherRepository;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    public List<Subject> getSubjectsByTeacherId(Long teacherId) {
        @SuppressWarnings("null")
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        if (teacher.isPresent()) {
            return new ArrayList<>(teacher.get().getSubjects());
        } else {
            return new ArrayList<>();
        }
    }

    @SuppressWarnings("null")
    public Teacher getTeachertById(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id " + teacherId));
    }

    public Teacher getTeacherByName(String name) {
        return teacherRepository.findByFirstName(name)
                .orElseThrow(() -> new RuntimeException("Teacher not found with name " + name));
    }

    public Teacher getTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Teacher not found with email " + email));
    }

    public List<Subject> getSubjectsByTeacherName(String name) {
        Optional<Teacher> teacher = teacherRepository.findByFirstName(name);

        if (teacher.isPresent()) {
            return new ArrayList<>(teacher.get().getSubjects());
        } else {
            return new ArrayList<>();
        }
    }

    public List<Subject> getSubjectsByTeacherEmail(String email) {
        Optional<Teacher> teacher = teacherRepository.findByEmail(email);

        if (teacher.isPresent()) {
            return new ArrayList<>(teacher.get().getSubjects());
        } else {
            return new ArrayList<>();
        }
    }

    public List<Teacher> getAllBySubjectsId(Long id){
        return teacherRepository.findAllBySubjectsId(id);
    }

    @SuppressWarnings("null")
    public Teacher getById(Long id){

        return teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with email " + id));

    }

    public Optional<Teacher> getOptionalByEmail(String email){
        return teacherRepository.findByEmail(email);

    }

    public Teacher getByEmail(String email){
        return teacherRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Teacher not found with email " + email));
    }

    @SuppressWarnings("null")
    public void setTeacher(Teacher teacher){
        teacherRepository.save(teacher);
    }

}
