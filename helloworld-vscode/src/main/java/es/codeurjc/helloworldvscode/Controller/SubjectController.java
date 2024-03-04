package es.codeurjc.helloworldvscode.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import es.codeurjc.helloworldvscode.Entitys.Admin;
import es.codeurjc.helloworldvscode.Entitys.Student;
import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.Entitys.Teacher;
import es.codeurjc.helloworldvscode.Entitys.User;
import es.codeurjc.helloworldvscode.enumerate.Role;
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

            if (user.get().getRole() == Role.ROLE_TEACHER) {
                modelAndView.addObject("isStudent", false);
                Teacher teacher = teacherService.getTeacherByEmail(principal.getName());
                modelAndView.addObject("user", teacher);

            } else if(user.get().getRole() == Role.ROLE_STUDENT){
                modelAndView.addObject("isStudent", true);
                Student student = studentService.getStudentByEmail(principal.getName());
                List<Subject> recommendedSubjects = subjectService.recommendSubjects(student);
                modelAndView.addObject("user", student);
                modelAndView.addObject("recommendedSubjects", recommendedSubjects);
            } else{
                modelAndView.addObject("isStudent", false);
                Admin admin = adminService.getAdminByEmail(principal.getName());
                modelAndView.addObject("user", admin);

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
        if (subjectService.unique(id).isPresent()) {
            modelView.setViewName("subject_info");
            model.addAttribute("subject", subjectService.unique(id).get());
            return modelView;
        } else {
            modelView.setViewName("error");
            return modelView;
        }
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
