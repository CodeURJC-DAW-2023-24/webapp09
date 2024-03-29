package es.codeurjc.backend.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import es.codeurjc.backend.model.*;
import es.codeurjc.backend.services.AdminService;
import es.codeurjc.backend.services.MailService;
import es.codeurjc.backend.services.StudentService;
import es.codeurjc.backend.services.SubjectService;
import es.codeurjc.backend.services.TeacherService;
import es.codeurjc.backend.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class SubjectController {

    @Autowired
    SubjectService subjectService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    AdminService adminService;
    @Autowired
    MailService mailService;
    @Autowired
    UserService userService;

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        if (principal != null) {

            model.addAttribute("logged", true);
            model.addAttribute("username", principal.getName());
            model.addAttribute("user", request.isUserInRole("USER"));

        } else {
            model.addAttribute("logged", false);
        }
    }

    @GetMapping("/")
    public ModelAndView showSubjects(Model model, HttpSession session, Pageable pageable, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main_page");

        modelAndView.addObject("isNotRegstered", false);

        List<Subject> recommendedSubjects = subjectService.recommendSubjects(null);
        modelAndView.addObject("recommendedSubjects", recommendedSubjects);

        // ajax
        modelAndView.addObject("moreSubjects", subjectService.getAllPage(PageRequest.of(0, 3)));

        return modelAndView;
    }

    @GetMapping("/subject/{id}")
    public ModelAndView uniqueEvent(HttpServletRequest request, Model model, @PathVariable Long id) {
        ModelAndView modelView = new ModelAndView();
        Principal principal = request.getUserPrincipal();

        boolean registered = false;

        if (principal != null) {
            Optional<User> user = userService.getByEmail(principal.getName());
            if (user.get().getRoles().contains("STUDENT")) {
                registered = true;
            }
        }

        if (subjectService.unique(id).isPresent()) {
            modelView.setViewName("subject_info");
            model.addAttribute("subject", subjectService.unique(id).get());

            model.addAttribute("isStudent", registered);
            return modelView;
        } else {
            modelView.setViewName("error");
            return modelView;
        }
    }

    @PostMapping("/subject/{id}/enroll")
    @ResponseBody
    public ResponseEntity<String> enrollInSubject(@PathVariable Long id, Principal principal) {

        Student student = studentService.getStudentByEmail(principal.getName());
        Subject subject = subjectService.getSubjectById(id);
        Teacher teacher = subject.getTeachers().get(0);

        mailService.enviarCorreo(teacher.getEmail(), student, subject);

        return ResponseEntity.ok("Enrolled successfully");
    }

    @GetMapping("/subjectInfo")
    public List<Subject> getSubjects(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return subjectService.getSubjects(pageable);
    }

    @GetMapping("/subjectUser")
    public List<Subject> getSubjectsUser(HttpServletRequest request, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return subjectService.getSubjectsUser(request, pageable);
    }

}
