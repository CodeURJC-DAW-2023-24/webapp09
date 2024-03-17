package es.codeurjc.backend.controller.rest;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.codeurjc.backend.model.Student;
import es.codeurjc.backend.model.Subject;
import es.codeurjc.backend.model.Teacher;
import es.codeurjc.backend.services.AdminService;
import es.codeurjc.backend.services.MailService;
import es.codeurjc.backend.services.StudentService;
import es.codeurjc.backend.services.SubjectService;
import es.codeurjc.backend.services.TeacherService;
import es.codeurjc.backend.services.UserService;

@RestController
@RequestMapping("/api/subjects")
public class SubjectRestController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private MailService mailService;

    @GetMapping("/")
    public List<Subject> getAllSubjects() {
        return subjectService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubjectById(@PathVariable Long id) {
        Optional<Subject> subject = subjectService.unique(id);
        return subject.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/enroll")
    public ResponseEntity<String> enrollInSubject(@PathVariable Long id, Principal principal) {
        Student student = studentService.getStudentByEmail(principal.getName());
        Subject subject = subjectService.getSubjectById(id);
        Teacher teacher = subject.getTeachers().get(0); 
        mailService.enviarCorreo(teacher.getEmail(), student, subject);
        return ResponseEntity.ok("Enrolled successfully");
    }


}

