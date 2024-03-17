package es.codeurjc.backend.services;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.codeurjc.backend.model.Student;
import es.codeurjc.backend.model.Subject;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.repository.SubjectRepository;
import es.codeurjc.backend.repository.UserRepository;
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

    
    @SuppressWarnings("null")
    public void deleteSubjectId(Long id){
        subjectRepository.deleteById(id);
    }

    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    public Page<Subject> getAllPage(Pageable page) {
        return subjectRepository.findAll(page);
    }

    @SuppressWarnings("null")
    public Optional<Subject> unique(Long id) {
        return subjectRepository.findById(id);
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
        Optional<User> user = userRepository.findByEmail(principal.getName());
        
        if (user.get().getRoles().contains("TEACHER")) {
            List<Subject> lista = teacherService.getSubjectsByTeacherEmail(principal.getName());
            List<Subject> subjects;

            if (startItem < lista.size()) {
                int toIndex = Math.min(startItem + pageSize, lista.size());
                subjects = lista.subList(startItem, toIndex);
            } else {
                subjects = Collections.emptyList();
            }
            return subjects;

        } else if (user.get().getRoles().contains("STUDENT")) {
            List<Subject> lista = studentService.getSubjectsByStudentEmail(principal.getName());
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

    @SuppressWarnings("null")
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
                .collect(Collectors.groupingBy(Subject::getGender, Collectors.counting())
        );

        // Ordenar genders por frecuencia
        List<String> gendersOrdered = countByGender.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList()
        );

        // Asegurar que al menos se incluyan asignaturas de los dos genders más frecuentes
        List<Subject> recommendedSubjects = new ArrayList<>();
        for (String gender : gendersOrdered) {
            List<Subject> subjectsByGender = findByGenderAndNotStudent(gender, student)
                    .stream()
                    .filter(s -> !student.getSubjects().contains(s))
                    .limit(5)
                    .collect(Collectors.toList()
            );

            recommendedSubjects.addAll(subjectsByGender);
            if (recommendedSubjects.size() >= 5) {
                break; // Asegurarse de no exceder 5 recomendaciones en total
            }
        }
        
        return recommendedSubjects.stream().limit(5).collect(Collectors.toList());
    }

    public Page<Subject> getAllNotEnrolled(List<Subject> subjectsStudent, Pageable page){

        List<Long> idSubejcts = new ArrayList<>();

        for(Subject sub: subjectsStudent){
            idSubejcts.add(sub.getId());
        }

        return subjectRepository.findAllByIdNotIn(idSubejcts, page);
    }

    public boolean isNameUnique(String name){
        return subjectRepository.findByName(name).isEmpty();

    }

    @SuppressWarnings("null")
    public Subject getById(Long id){

        return subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with email " + id));

    }

    @SuppressWarnings("null")
    public void setSubject(Subject subject){
        subjectRepository.save(subject);
    }

    public List<Object[]> countSubjectsByGender(){
        return subjectRepository.countSubjectsByGender();
    }

}