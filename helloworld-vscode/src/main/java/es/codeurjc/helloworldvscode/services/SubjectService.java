package es.codeurjc.helloworldvscode.services;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.codeurjc.helloworldvscode.Entitys.Student;
import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.Entitys.User;
import es.codeurjc.helloworldvscode.repository.SubjectRepository;
import es.codeurjc.helloworldvscode.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public Optional<Subject> unique(Long id) {
        return subjectRepository.findSubjectById(id);
    }

    public List<Subject> getSubjects(Pageable pageable) {
        Page<Subject> subjectPage = subjectRepository.findAll(pageable);
        return subjectPage.getContent();
    }

    public List<Subject> getSubjectsUser(HttpServletRequest request, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        Principal principal = request.getUserPrincipal();
        Optional<User> u = userRepository.findByEmail(principal.getName());
        User usuario = u.get();
        String rol = usuario.getRole().toString();
        if (rol == "ROLE_TEACHER") {
            List<Subject> lista = teacherService.findSubjectsByTeacherEmail(principal.getName());
            List<Subject> subjects;

            if (startItem < lista.size()) {
                int toIndex = Math.min(startItem + pageSize, lista.size());
                subjects = lista.subList(startItem, toIndex);
            } else {
                subjects = Collections.emptyList();
            }
            System.out.println("--------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------" + lista);
            return subjects;

        } else if (rol == "ROLE_STUDENT") {
            List<Subject> lista = studentService.findSubjectsByStudentEmail(principal.getName());
            List<Subject> subjects;

            if (startItem < lista.size()) {
                int toIndex = Math.min(startItem + pageSize, lista.size());
                subjects = lista.subList(startItem, toIndex);
            } else {
                subjects = Collections.emptyList();
            }
            return subjects;
        }
        return null;
        
    }

    public Subject getSubjectById(Long studentId) {
        // Retorna el estudiante o lanza una excepción si no se encuentra
        return subjectRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + studentId));
    }

    public List<Subject> findByGenderAndNotStudent(String gender, Student student) {
        // Obtener todas las asignaturas por género
        List<Subject> subjectsByGender = subjectRepository.findByGender(gender);

        if (student == null) {
            return subjectsByGender;
        }

        // Filtrar las asignaturas que el estudiante ya ha cursado
        List<Subject> notCoursedSubjects = subjectsByGender.stream()
                .filter(subject -> !student.getSubjects().contains(subject))
                .collect(Collectors.toList());

        return notCoursedSubjects;
    }

    public List<Subject> recommendSubjects(Student student) {
        if ((student == null) || student.getSubjects().size() == 0) {
            // Devolver las 5 asignaturas más populares
            return subjectRepository.findAll().stream()
                    .sorted(Comparator.comparingInt((Subject s) -> s.getStudents().size()).reversed())
                    .limit(5)
                    .collect(Collectors.toList());
        }

        // Contar las asignaturas cursadas por gender
        Map<String, Long> countByGender = student.getSubjects().stream()
                .collect(Collectors.groupingBy(Subject::getGender, Collectors.counting()));

        // Ordenar genders por frecuencia
        List<String> gendersOrdered = countByGender.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Asegurar que al menos se incluyan asignaturas de los dos genders más
        // frecuentes
        List<Subject> recommendedSubjects = new ArrayList<>();
        for (String gender : gendersOrdered) {
            List<Subject> subjectsByGender = findByGenderAndNotStudent(gender, student)
                    .stream()
                    .filter(s -> !student.getSubjects().contains(s))
                    .limit(5)
                    .collect(Collectors.toList());

            recommendedSubjects.addAll(subjectsByGender);
            if (recommendedSubjects.size() >= 5) {
                break; // Asegurarse de no exceder 5 recomendaciones en total
            }
        }
        return recommendedSubjects.stream().limit(5).collect(Collectors.toList());
    }

}