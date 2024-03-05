package es.codeurjc.helloworldvscode.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import es.codeurjc.helloworldvscode.Entitys.*;
import es.codeurjc.helloworldvscode.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import es.codeurjc.helloworldvscode.repository.AdminRepository;
import es.codeurjc.helloworldvscode.repository.SubjectRepository;
import es.codeurjc.helloworldvscode.repository.UserRepository;
import es.codeurjc.helloworldvscode.services.AdminService;
import es.codeurjc.helloworldvscode.services.StudentService;
import es.codeurjc.helloworldvscode.services.SubjectService;
import es.codeurjc.helloworldvscode.services.TeacherService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class SubjectController {

    @Autowired
    SubjectRepository subjectsList;
    @Autowired
    SubjectService subjectService;
    @Autowired
    StudentService studentService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TeacherService teacherService;

    @Autowired
    ForumRepository forumRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AdminService adminService;

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
        Principal principal = request.getUserPrincipal();
        model.addAttribute("subjects", subjectsList.findAll());
        modelAndView.setViewName("main_page");

        if (principal != null) {
            Optional<User> user = userRepository.findByEmail(principal.getName());

            if (user.get().getRoles().contains("TEACHER")) {
                modelAndView.addObject("isStudent", false);
                Teacher teacher = teacherService.getTeacherByEmail(principal.getName());
                modelAndView.addObject("user", teacher);

            } else if(user.get().getRoles().contains("STUDENT")){
                modelAndView.addObject("isStudent", true);
                Student student = studentService.getStudentByEmail(principal.getName());
                List<Subject> recommendedSubjects = subjectService.recommendSubjects(student);
                modelAndView.addObject("user", student);
                modelAndView.addObject("recommendedSubjects", recommendedSubjects);
            } else if (user.get().getRoles().contains("ADMIN")){
                modelAndView.setViewName("subjects_admin");
                modelAndView.addObject("isStudent", false);
                Admin admin = adminService.getAdminByEmail(principal.getName());
                modelAndView.addObject("user", admin);
                ArrayList<Subject> subjects = (ArrayList<Subject>) subjectService.findAll(); 
                modelAndView.addObject("subjects", subjects);

            }
        } else {
            modelAndView.addObject("isStudent", true);
            List<Subject> recommendedSubjects = subjectService.recommendSubjects(null);
            modelAndView.addObject("recommendedSubjects", recommendedSubjects);
        }
        return modelAndView;
    }

    @GetMapping("/subject/{id}")
    public ModelAndView uniqueEvent(HttpServletRequest request, Model model, @PathVariable Long id) {
        ModelAndView modelView = new ModelAndView();
        Principal principal =  request.getUserPrincipal();

        boolean registered=principal!=null;

        if (subjectService.unique(id).isPresent()) {
            modelView.setViewName("subject_info");
            model.addAttribute("subject", subjectService.unique(id).get());
            model.addAttribute("registered", registered);
            return modelView;
        } else {
            modelView.setViewName("error");
            return modelView;
        }
    }

    @PostMapping("/subject/{id}/enroll")
    @ResponseBody
    public ResponseEntity<String> enrollInSubject(@PathVariable Long id, Principal principal) {
        String comment="The student wants to enroll this subject: "+subjectService.getSubjectById(id).getName();
        Forum forum = new Forum(principal.getName(), comment, null); // Asume que tienes un constructor adecuado
        forumRepository.save(forum);

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
