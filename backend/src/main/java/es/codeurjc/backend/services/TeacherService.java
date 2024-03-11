package es.codeurjc.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.backend.Entitys.Subject;
import es.codeurjc.backend.Entitys.Teacher;
import es.codeurjc.backend.repository.TeacherRepository;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    public List<Subject> findSubjectsByTeacherId(Long teacherId) {
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

    public List<Subject> findSubjectsByTeacherName(String name) {
        Optional<Teacher> teacher = teacherRepository.findByFirstName(name);

        if (teacher.isPresent()) {
            return new ArrayList<>(teacher.get().getSubjects());
        } else {
            return new ArrayList<>();
        }
    }

    public List<Subject> findSubjectsByTeacherEmail(String email) {
        Optional<Teacher> teacher = teacherRepository.findByEmail(email);

        if (teacher.isPresent()) {
            return new ArrayList<>(teacher.get().getSubjects());
        } else {
            return new ArrayList<>();
        }
    }
}