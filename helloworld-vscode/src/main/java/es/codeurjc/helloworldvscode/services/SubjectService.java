package es.codeurjc.helloworldvscode.services;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
        List<Subject> lista = studentService.findSubjectsByStudentName(principal.getName());
    
        List<Subject> subjects;
    
        if (startItem < lista.size()) {
            int toIndex = Math.min(startItem + pageSize, lista.size());
            subjects = lista.subList(startItem, toIndex);
        } else {
            subjects = Collections.emptyList();
        }
        System.out.println("----------------------------------------------------------------"+subjects);
    
        return subjects;
    }

    public Subject getSubjectById(Long studentId) {
        // Retorna el estudiante o lanza una excepción si no se encuentra
        return subjectRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + studentId));
    }
}
