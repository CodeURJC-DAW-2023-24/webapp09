package es.codeurjc.helloworldvscode;

import es.codeurjc.helloworldvscode.Entitys.*;
import es.codeurjc.helloworldvscode.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

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

                //////// STUDENTS
                Student st1 = new Student("Juan", "Pérez", "jj@gmail.com", "jj");
                Student st2 = new Student("Ana", "López", "ana.lopez@example.com", "password456");
                Student st3 = new Student("Carlos", "García", "carlos.garcia@example.com", "password789");
                Student st4 = new Student("Luisa", "Martín", "luisa.martin@example.com", "password101");
                Student st5 = new Student("Juan", "Gomez", "juan@gmail.com", "juan");
                Student st6 = new Student("Pablo", "Rollo", "pablo@gmail.com", "pablo");
                Student st7 = new Student("Cris", "Sanz", "cris@gmail.com", "cris");
                Student st8 = new Student("Lucas", "Pavo", "lucas@gmail.com", "pavo");
                Student st9 = new Student("Pau", "Rios", "pau@gmail.com", "pau");
                Student st10 = new Student("Leo", "Conde", "leo@gmail.com", "leo");

                //////// TEACHERS
                Teacher t1 = new Teacher("aa", "Esteban", "aa@gmail.com", "aa");
                Teacher t3 = new Teacher("Pepe", "Franco", "pfespania@gmail.com", "vivaespaña");
                Teacher t2 = new Teacher("Belen", "Esteban", "salavame@gmail.com", "andreita");

                Teacher t4 = new Teacher("Belen", "Esteban", "aaaaurjc@gmail.com", "aaaaurjc.09");

                //////// ADMIN
                Admin a1 = new Admin("ss", "Sandra", "ss@gmail.com", "ss");
                Admin a2 = new Admin("dd", "Daniel", "dd@gmail.com", "dd");

                //////// SUBJECTS
                Subject s1 = new Subject("Matemáticas",
                                "Estudio de los números, cantidades, y formas.",
                                "Curso completo de Matemáticas incluyendo álgebra, geometría y cálculo.",
                                "Ciencias Exactas");
                Subject s2 = new Subject("Historia",
                                "Estudio de los eventos pasados.",
                                "Curso completo de Historia cubriendo eventos desde la antigüedad hasta el presente.",
                                "Ciencias Sociales");
                Subject s3 = new Subject("Biología",
                                "Ciencia que estudia la vida.",
                                "Curso completo de Biología, incluyendo botánica, zoología y ecología.",
                                "Ciencias Naturales");
                Subject s4 = new Subject("Literatura",
                                "Estudio de obras literarias.",
                                "Curso completo de Literatura, incluyendo análisis de textos y figuras literarias.",
                                "Humanidades");
                Subject s5 = new Subject("Estructuras de Datos",
                                "La asignatura introduce estructuras de datos clásicas con enfoque en TAD y programación. Los alumnos aprenderán propiedades, funcionamiento e implementaciones para resolver problemas.",
                                "Las estructuras de datos y algoritmos son fundamentales en Informática. Se enseñan en el segundo cuatrimestre del primer curso, continuando la asignatura de Introducción a la Programación.",
                                "Programación");

                Subject s6 = new Subject("Estructuras de Datos",
                                "La asignatura introduce estructuras de datos clásicas con enfoque en TAD y programación. Los alumnos aprenderán propiedades, funcionamiento e implementaciones para resolver problemas.",
                                "Las estructuras de datos y algoritmos son fundamentales en Informática. Se enseñan en el segundo cuatrimestre del primer curso, continuando la asignatura de Introducción a la Programación.",
                                "Programación");
                Subject s7 = new Subject("Desarrollo de Aplicaciones Web",
                                "La asignatura ofrece una visión general del desarrollo de aplicaciones web para diversos dispositivos. Cubre características esenciales, tecnologías, arquitecturas y protocolos comunes.",
                                "Las aplicaciones web son vitales en informática. La asignatura enseña el desarrollo web para crear aplicaciones profesionales.",
                                "Programación",
                                "/images/blog-post-daw.jpg");
                Subject s8 = new Subject("Inteligencia Artificial",
                                "La asignatura explora los fundamentos y aplicaciones de la inteligencia artificial. Los estudiantes estudiarán algoritmos de búsqueda, aprendizaje automático, lógica difusa y sistemas expertos.",
                                "Proporciona una visión general de los fundamentos y aplicaciones de la IA. El objetivo es comprender y aplicar técnicas avanzadas de IA en diferentes campos.",
                                "Aprendizaje automático",
                                "/images/blog-post-ia.jpg");
                Subject s9 = new Subject("Introducción a las Bases de Datos",
                                "Los estudiantes aprenderán sobre diseño, implementación y optimización de bases de datos. Se explorarán temas como SQL, modelado de datos y gestión de transacciones.",
                                "Bases de Datos introduce los conceptos esenciales de las bases de datos relacionales y no relacionales. Los estudiantes adquieren habilidades en diseño, implementación y gestión de bases de datos para aplicaciones modernas.",
                                "Programacion");

                Subject s10 = new Subject("Matemáticas Avanzadas",
                                "Conceptos avanzados de matemáticas incluyendo cálculo multivariable y ecuaciones diferenciales.",
                                "Este curso profundiza en el estudio de las matemáticas más allá del cálculo básico, preparando a los estudiantes para aplicaciones complejas en ciencias e ingeniería.",
                                "Ciencias Exactas");
                Subject s11 = new Subject("Programación Funcional",
                                "Fundamentos de programación funcional usando lenguajes como Haskell o Scala.",
                                "Este curso introduce a los estudiantes a los principios de la programación funcional, enfocándose en la inmutabilidad, funciones de primera clase, y más.",
                                "Programación");
                Subject s12 = new Subject("Algoritmos y Complejidad",
                                "Estudio de algoritmos avanzados y teoría de la complejidad computacional.",
                                "Los estudiantes aprenderán sobre algoritmos de grafos, búsqueda y ordenamiento, así como nociones fundamentales de complejidad.",
                                "Programación");
                Subject s13 = new Subject("Física Cuántica",
                                "Introducción a los principios y aplicaciones de la física cuántica.",
                                "Curso diseñado para estudiantes de ciencias exactas interesados en los fundamentos y las aplicaciones revolucionarias de la mecánica cuántica.",
                                "Ciencias Exactas");
                Subject s14 = new Subject("Desarrollo de Software Ágil",
                                "Metodologías ágiles para el desarrollo de software, incluyendo Scrum y Kanban.",
                                "Este curso cubre las prácticas y principios del desarrollo ágil de software, preparando a los estudiantes para trabajar en entornos de desarrollo modernos.",
                                "Programación");
                Subject s15 = new Subject("Geografía Mundial",
                                "Estudio de las características físicas de la Tierra y su impacto en las sociedades humanas.",
                                "Este curso ofrece una visión global de la geografía física y política, enfocándose en cómo los entornos naturales influyen en las actividades humanas.",
                                "Ciencias Sociales");

                Subject s16 = new Subject("Psicología General",
                                "Introducción a los principales conceptos, teorías y debates dentro del campo de la psicología.",
                                "Los estudiantes explorarán las diversas facetas de la psicología, incluyendo el desarrollo humano, la cognición, el comportamiento social, y la salud mental.",
                                "Ciencias Sociales");

                Subject s17 = new Subject("Sociología de la Educación",
                                "Análisis de los sistemas educativos y su impacto en las estructuras sociales y individuales.",
                                "Este curso examina cómo la educación afecta a la sociedad y viceversa, explorando temas como la desigualdad, la política educativa y el cambio social.",
                                "Ciencias Sociales");

                Subject s18 = new Subject("Historia del Arte",
                                "Recorrido por la evolución del arte a lo largo de la historia, desde la antigüedad hasta la era moderna.",
                                "Los estudiantes aprenderán sobre diferentes movimientos artísticos, obras icónicas, y figuras influyentes en el mundo del arte.",
                                "Humanidades");

                Subject s19 = new Subject("Filosofía Política",
                                "Estudio de las ideas y principios que han moldeado los sistemas políticos y sociales a lo largo de la historia.",
                                "Este curso aborda teorías sobre el estado, la justicia, la libertad, y la democracia, y su aplicación en el análisis de problemas contemporáneos.",
                                "Humanidades");

                Subject s20 = new Subject("Antropología Cultural",
                                "Exploración de la diversidad cultural de la humanidad y cómo las culturas se desarrollan y cambian a lo largo del tiempo.",
                                "A través de estudios de caso, los estudiantes examinarán prácticas, creencias y relaciones sociales en diferentes culturas alrededor del mundo.",
                                "Ciencias Sociales");
                //////// FORUMS
                Forum f1 = new Forum("Manolo Perez",
                                "I'm currently delving into Python programming and have stumbled upon the topic of list comprehensions. While I've grasped the basic concept, I find myself a bit perplexed, particularly when it comes to nested list comprehensions and their comparison to nested loops.\r\n"
                                                + //
                                                "\r\n" +
                                                "Could you kindly elucidate the intricacies of list comprehensions in Python, especially in the context of nested structures? ");
                Forum f2 = new Forum("Call me by your name",
                                "I am currently immersing myself in Python programming and have encountered the concept of list comprehensions. Although I've managed to grasp the fundamental concept, I find myself encountering difficulties, particularly in understanding nested list comprehensions and their correlation with nested loops.\r\n"
                                                + //
                                                "\r\n" +
                                                "Would you be able to provide some guidance on the complexities of list comprehensions in Python, especially within the realm of nested structures? Any clarifying examples or recommended learning materials would be tremendously beneficial to my comprehension.");
                Forum f3 = new Forum("Belen Estaban",
                                "As I continue my journey into Python programming, I've reached a point where I'm exploring list comprehensions. While I've managed to grasp the fundamental concept, I'm encountering challenges, particularly in understanding nested list comprehensions and their comparison to nested loops.\r\n"
                                                + //
                                                "\r\n" + //
                                                "Could you kindly provide some insights into the intricacies of list comprehensions in Python, especially within the context of nested structures?");
                Forum f4 = new Forum("Paquito Chocolates",
                                "I'm currently engaged in learning Python programming and have reached a section on list comprehensions. While I've attained a basic understanding, I find myself facing difficulties, particularly concerning nested list comprehensions and their relationship with nested loops.\r\n"
                                                + //
                                                "\r\n" + //
                                                "Could you please offer some clarification on the complexities of list comprehensions in Python, particularly within the realm of nested structures? Any additional examples or recommended reading materials would be immensely helpful in solidifying my understanding.\r\n"
                                                + //
                                                "\r\n" + //
                                                "Thank you for your time and guidance.");

                byte[] blanco = getFile();

                //////// EXAMS
                Exam e1 = new Exam("Exam 1", "pdf", blanco, s4);
                Exam e2 = new Exam("Exam 2", "pdf", blanco, s4);
                Exam e3 = new Exam("Exam 3", "pdf", blanco, s4);
                Exam e4 = new Exam("Final Exam", "pdf", blanco, s4);

                //////// EXAMSTUDENTS
                ExamStudent es1 = new ExamStudent(3.45, "pdf", blanco, e1, st1);
                ExamStudent es2 = new ExamStudent(10, "pdf", blanco, e2, st1);
                ExamStudent es3 = new ExamStudent(8.35, "pdf", blanco, e3, st1);
                ExamStudent es4 = new ExamStudent(5.04, "pdf", blanco, e4, st1);
                ExamStudent es5 = new ExamStudent(2.45, "pdf", blanco, e1, st2);
                ExamStudent es6 = new ExamStudent(0, "pdf", blanco, e1, st2);

                // students IN subjects

                s1.getStudents().addAll(List.of(st1, st2));
                s2.getStudents().addAll(List.of(st1, st3, st8));
                s3.getStudents().addAll(List.of(st1, st4));
                s4.getStudents().addAll(List.of(st1, st2, st7, st8, st9, st10));
                s5.getStudents().addAll(List.of(st1));
                s6.getStudents().addAll(List.of(st1));
                s7.getStudents().addAll(List.of(st1));
                s8.getStudents().addAll(List.of(st1));
                s9.getStudents().addAll(List.of(st1));
                s10.getStudents().addAll(List.of(st1));
                s11.getStudents().addAll(List.of());
                s12.getStudents().addAll(List.of());
                s13.getStudents().addAll(List.of());
                s14.getStudents().addAll(List.of());
                s15.getStudents().addAll(List.of(st8));
                s16.getStudents().addAll(List.of(st8));
                s17.getStudents().addAll(List.of(st8, st9, st10));
                s18.getStudents().addAll(List.of());
                s19.getStudents().addAll(List.of());
                s20.getStudents().addAll(List.of());

                // teachers IN subjects
                s4.getTeachers().addAll(List.of(t1,t4));
                s5.getTeachers().addAll(List.of(t1,t4));
                s6.getTeachers().addAll(List.of(t1,t4));
                s1.getTeachers().addAll(List.of(t2,t4));
                s20.getTeachers().addAll(List.of(t3,t4));

                // forums IN subjects
                s4.getForums().addAll(List.of(f1, f2, f3, f4));

                // subjects IN students
                st1.getSubjects().addAll(List.of(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10));
                st2.getSubjects().addAll(List.of(s1, s4));
                st3.getSubjects().addAll(List.of(s2));
                st4.getSubjects().addAll(List.of(s3));
                st7.getSubjects().addAll(List.of(s4));
                st8.getSubjects().addAll(List.of(s4, s15, s16, s17, s2));
                st9.getSubjects().addAll(List.of(s4, s17));
                st10.getSubjects().addAll(List.of(s4, s17));

                // subjects IN teachers

                t1.getSubjects().addAll(List.of(s4, s5, s6));
                t2.getSubjects().addAll(List.of(s1));
                t3.getSubjects().addAll(List.of(s20));

                // subjects IN forums
                f1.setSubject(s4);
                f2.setSubject(s4);
                f3.setSubject(s4);
                f4.setSubject(s4);

                // Guardar todos los datos en listas
                studentRepository.saveAll(List.of(st1, st2, st3, st4, st5, st6, st7, st8, st9, st10));
                teacherRepository.saveAll(List.of(t1, t2, t3));
                subjectRepository.saveAll(List.of(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16,
                                s17, s18, s19, s20));
                forumRepository.saveAll(List.of(f1, f2, f3, f4));
                examRepository.saveAll(List.of(e1, e2, e3, e4));
                examStudentRepository.saveAll(List.of(es1, es2, es3, es4, es5, es6));
                adminRepository.saveAll(List.of(a1, a2));

                //////// TEACHER

        }


        private byte[] getFile() throws IOException{
                String filePath = "C:\\Users\\amand\\Documents\\GitHub\\webapp09\\helloworld-vscode\\src\\main\\java\\es\\codeurjc\\helloworldvscode\\testDataInitializer.java";
                File pdfFile = new File(filePath);

                byte[] fileBytes = Files.readAllBytes(pdfFile.toPath());
                
                return fileBytes;
        }
}