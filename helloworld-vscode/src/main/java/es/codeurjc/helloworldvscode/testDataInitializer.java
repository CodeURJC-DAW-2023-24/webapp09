package es.codeurjc.helloworldvscode;


import es.codeurjc.helloworldvscode.Entitys.Exam;
import es.codeurjc.helloworldvscode.Entitys.ExamStudent;
import es.codeurjc.helloworldvscode.Entitys.Student;
import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ExamStudentRepository examStudentRepository;
    @Autowired
    private ForumRepository forumRepository;

    @PostConstruct
    public void init() throws Exception {



        // TEACHER





        // Crear estudiantes
        Student student1 = new Student("Juan", "Pérez", "juan.perez@example.com", "password123");
        Student student2 = new Student("Ana", "López", "ana.lopez@example.com", "password456");
        Student student3 = new Student("Carlos", "García", "carlos.garcia@example.com", "password789");
        Student student4 = new Student("Luisa", "Martín", "luisa.martin@example.com", "password101");

        Subject subject1 = new Subject("Matemáticas", "Estudio de los números, cantidades, y formas.", "Curso completo de Matemáticas incluyendo álgebra, geometría y cálculo.", "Ciencias Exactas");
        Subject subject2 = new Subject("Historia", "Estudio de los eventos pasados.", "Curso completo de Historia cubriendo eventos desde la antigüedad hasta el presente.", "Ciencias Sociales");
        Subject subject3 = new Subject("Biología", "Ciencia que estudia la vida.", "Curso completo de Biología, incluyendo botánica, zoología y ecología.", "Ciencias Naturales");
        Subject subject4 = new Subject("Literatura", "Estudio de obras literarias.", "Curso completo de Literatura, incluyendo análisis de textos y figuras literarias.", "Humanidades");


        Subject s1 = new Subject("Estructuras de Datos",
                "La asignatura introduce estructuras de datos clásicas con enfoque en TAD y programación. Los alumnos aprenderán propiedades, funcionamiento e implementaciones para resolver problemas.",
                "Las estructuras de datos y algoritmos son fundamentales en Informática. Se enseñan en el segundo cuatrimestre del primer curso, continuando la asignatura de Introducción a la Programación.",
                "Programación");
        Subject s2 = new Subject("Desarrollo de Aplicaciones Web",
                "La asignatura ofrece una visión general del desarrollo de aplicaciones web para diversos dispositivos. Cubre características esenciales, tecnologías, arquitecturas y protocolos comunes.",
                "Las aplicaciones web son vitales en informática. La asignatura enseña el desarrollo web para crear aplicaciones profesionales.",
                "Programación", "/images/blog-post-daw.jpg");
        Subject s3 = new Subject("Inteligencia Artificial",
                "La asignatura explora los fundamentos y aplicaciones de la inteligencia artificial. Los estudiantes estudiarán algoritmos de búsqueda, aprendizaje automático, lógica difusa y sistemas expertos.",
                "Proporciona una visión general de los fundamentos y aplicaciones de la IA. El objetivo es comprender y aplicar técnicas avanzadas de IA en diferentes campos.",
                "Aprendizaje automático", "/images/blog-post-ia.jpg");
        Subject s4 = new Subject("Introducción a las Bases de Datos",
                "Los estudiantes aprenderán sobre diseño, implementación y optimización de bases de datos. Se explorarán temas como SQL, modelado de datos y gestión de transacciones.",
                "Bases de Datos introduce los conceptos esenciales de las bases de datos relacionales y no relacionales. Los estudiantes adquieren habilidades en diseño, implementación y gestión de bases de datos para aplicaciones modernas.",
                "Bases de datos");



        // STUDENT
        Student st1 = new Student("Juann", "Gomez", "juan@gmail.com", "juan");
        st1.getSubjects().add(s4);
        s4.getStudents().add(st1);

        Student st2 = new Student("Pablo", "Rollo", "pablo@gmail.com", "pablo");
        st2.getSubjects().add(s4);
        s4.getStudents().add(st2);

        Student st3 = new Student("Cris", "Sanz", "cris@gmail.com", "cris");
        st3.getSubjects().add(s4);
        s4.getStudents().add(st3);

        Student st4 = new Student("Lucas", "Pavo", "lucas@gmail.com", "pavo");
        st4.getSubjects().add(s4);
        s4.getStudents().add(st4);

        Student st5 = new Student("Pau", "Rios", "pau@gmail.com", "pau");
        st5.getSubjects().add(s4);
        s4.getStudents().add(st5);

        Student st6 = new Student("Leo", "Conde", "leo@gmail.com", "leo");
        st6.getSubjects().add(s4);
        s4.getStudents().add(st6);


        Exam e1 = new Exam("Exam 1", null, null, s4);
        Exam e2 = new Exam("Exam 2", null, null, s4);
        Exam e3 = new Exam("Exam 3", null, null, s4);
        Exam e4 = new Exam("Final Exam", null, null, s4);


        ExamStudent es1 = new ExamStudent(3.45, null, null, e1, st1);
        ExamStudent es2 = new ExamStudent(10, null, null, e2, st1);
        ExamStudent es3 = new ExamStudent(8.35, null, null, e3, st1);
        ExamStudent es4 = new ExamStudent(5.04, null, null, e4, st1);
        ExamStudent es5 = new ExamStudent(2.45, null, null, e1, st2);
        ExamStudent es6 = new ExamStudent(0, null, null, e1, st2);

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



        // EXAMS STUDENT



        // Guardar estudiantes y asignaturas
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);

        studentRepository.save(st1);
        studentRepository.save(st2);
        studentRepository.save(st3);
        studentRepository.save(st4);
        studentRepository.save(st5);
        studentRepository.save(st6);


        subjectRepository.save(subject1);
        subjectRepository.save(subject2);
        subjectRepository.save(subject3);
        subjectRepository.save(subject4);
        subjectRepository.save(s1);
        subjectRepository.save(s2);
        subjectRepository.save(s3);
        subjectRepository.save(s4);

        // EXAMS

        examRepository.save(e1);
        examRepository.save(e2);
        examRepository.save(e3);
        examRepository.save(e4);




        examStudentRepository.save(es1);
        examStudentRepository.save(es2);
        examStudentRepository.save(es3);
        examStudentRepository.save(es4);
        examStudentRepository.save(es5);
        examStudentRepository.save(es6);

    }
}
