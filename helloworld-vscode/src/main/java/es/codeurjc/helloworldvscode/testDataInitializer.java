package es.codeurjc.helloworldvscode;


import es.codeurjc.helloworldvscode.Entitys.*;
import es.codeurjc.helloworldvscode.repository.*;
import es.codeurjc.helloworldvscode.repository.AdminRepository;
import es.codeurjc.helloworldvscode.repository.StudentRepository;
import es.codeurjc.helloworldvscode.repository.SubjectRepository;
import es.codeurjc.helloworldvscode.repository.TeacherRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;

@Service
public class testDataInitializer {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private ForumRepository forumRepository;

    @PostConstruct
    public void init() throws Exception {

        // Crear estudiantes
        Student student1 = new Student("Juan", "Pérez", "juan.perez@example.com", "password123");
        Student student2 = new Student("Ana", "López", "ana.lopez@example.com", "password456");
        Student student3 = new Student("Carlos", "García", "carlos.garcia@example.com", "password789");
        Student student4 = new Student("Luisa", "Martín", "luisa.martin@example.com", "password101");

        Subject subject1 = new Subject("Matemáticas", "Estudio de los números, cantidades, y formas.", "Curso completo de Matemáticas incluyendo álgebra, geometría y cálculo.", "Ciencias Exactas", "/images/maths.jpg");
        Subject subject2 = new Subject("Historia", "Estudio de los eventos pasados.", "Curso completo de Historia cubriendo eventos desde la antigüedad hasta el presente.", "Ciencias Sociales", "/images/history.jpg");
        Subject subject3 = new Subject("Biología", "Ciencia que estudia la vida.", "Curso completo de Biología, incluyendo botánica, zoología y ecología.", "Ciencias Naturales", "/images/biology.jpg");
        Subject subject4 = new Subject("Literatura", "Estudio de obras literarias.", "Curso completo de Literatura, incluyendo análisis de textos y figuras literarias.", "Humanidades", "/images/literature.jpg");

        // Relacionar estudiantes y asignaturas
        subject1.getStudents().add(student1);
        subject1.getStudents().add(student2);
        subject2.getStudents().add(student3);
        subject3.getStudents().add(student4);
        subject4.getStudents().add(student1);

        student1.getSubjects().add(subject1);
        student2.getSubjects().add(subject1);
        student3.getSubjects().add(subject2);
        student4.getSubjects().add(subject3);
        student1.getSubjects().add(subject4);

        // Guardar estudiantes y asignaturas
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);

        subjectRepository.save(subject1);
        subjectRepository.save(subject2);
        subjectRepository.save(subject3);
        subjectRepository.save(subject4);
    }
}
