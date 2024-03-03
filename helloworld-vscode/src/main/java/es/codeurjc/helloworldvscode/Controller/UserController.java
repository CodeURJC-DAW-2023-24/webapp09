package es.codeurjc.helloworldvscode.Controller;

import es.codeurjc.helloworldvscode.Entitys.*;
import es.codeurjc.helloworldvscode.enumerate.Role;
import es.codeurjc.helloworldvscode.repository.StudentRepository;
import es.codeurjc.helloworldvscode.repository.SubjectRepository;
import es.codeurjc.helloworldvscode.repository.TeacherRepository;
import es.codeurjc.helloworldvscode.repository.UserRepository;
import es.codeurjc.helloworldvscode.services.StudentService;
import es.codeurjc.helloworldvscode.services.SubjectService;
import es.codeurjc.helloworldvscode.services.TeacherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import java.security.Principal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class UserController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;


    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/logout")
    public String showLogout() {
        // Invalidar la sesión del usuario
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/login?logout"; // Redireccionar a la página de login con un mensaje de logout
    }

    @GetMapping("/subjects_registereduser")
    public ModelAndView showSubjects(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        ModelAndView modelAndView = new ModelAndView("subjects_registereduser");
        Optional<User> user = userRepository.findByEmail(principal.getName());
        if (user.get().getRole() == Role.ROLE_TEACHER) {
            modelAndView.addObject("isStudent", false);

            Teacher teacher = teacherService.getTeacherByEmail(principal.getName());
            modelAndView.addObject("user", teacher);
            
        } else {
            modelAndView.addObject("isStudent", true);
            Student student = studentService.getStudentByEmail(principal.getName());
            List<Subject> recommendedSubjects = subjectService.recommendSubjects(student);
            modelAndView.addObject("user", student);
            modelAndView.addObject("recommendedSubjects", recommendedSubjects);
        }
        return modelAndView;
    }
    
    @GetMapping("/redirection/{subjectId}")
    public String redirectURL(HttpServletRequest request, @PathVariable Long subjectId) {
        Principal principal = request.getUserPrincipal();
        Optional<User> user = userRepository.findByEmail(principal.getName());
        String url = "";
        if (user.get().getRole() == Role.ROLE_STUDENT){
            url = "/subject_onesubj_student/" + subjectId;
        } else if (user.get().getRole() == Role.ROLE_TEACHER){
            url = "/teachers/subject/" + subjectId + "/generalinformation";
        }
        
        return ("redirect:" + url);
    }
    

    @RequestMapping("/loginerror")
    public String loginerror() {
        return "loginerror";
    }

    @GetMapping("/sign-up")
    public String showSignup() {
        return "signup";
    }

    @PostMapping("/sign-up")
    public String showSignup(HttpServletRequest request,
            Model model,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String confirmEmail,
            @RequestParam String password,
            @RequestParam String confirmPassword) {
               
            List<Student> students =studentRepository.findAll();
        // Aquí podrías incluir validaciones, por ejemplo, verificar que los correos y
        // contraseñas coincidan
        if (!email.equals(confirmEmail)) {            
            model.addAttribute("error", "Emails do not match!");
            return "signup"; // Vuelve a la página de registro si hay un error
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match!");
            return "signup"; // Vuelve a la página de registro si hay un error
        }
        // Crear y guardar el nuevo usuario
        Student newStudent = new Student(firstName, lastName, email, password);
        studentRepository.save(newStudent);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public ModelAndView personalArea(Model model, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("profile");
        model.addAttribute("username", request.getUserPrincipal().getName());
        String role = "UNKNOWN"; // Por defecto
        if (request.isUserInRole("STUDENT")) {
            role = "Student";
        } else if (request.isUserInRole("TEACHER")) {
            role = "Teacher";
        }

        model.addAttribute("role", role);
        List<Subject> subjects = new ArrayList<>();
        String username = request.getUserPrincipal().getName();
        Optional<Student> student = studentRepository.findByEmail(username);
        Optional<Teacher> teacher = teacherRepository.findByEmail(username);
        if (student.isPresent()) {
            // Si se encuentra un estudiante, obtener las asignaturas asociadas
            subjects = studentService.findSubjectsByStudentId(student.get().getId());
        } else if (teacher.isPresent()) {
            // Si se encuentra un profesor, obtener las asignaturas asociadas
            subjects = teacherService.findSubjectsByTeacherId(teacher.get().getId());
        }

        // Crear el modelo y agregar las asignaturas
        modelAndView.addObject("subjects", subjects);
        return modelAndView;
    }

    @GetMapping("/editProfile")
    public String showEditProfile() {
        return "editProfile";
    }

    @PostMapping("/editProfile")
    public String editarPerfil(HttpServletRequest request,
                               Model model,
                               @RequestParam("firstName") Optional<String> firstName,
                               @RequestParam("lastName") Optional<String> lastName,
                               @RequestParam("email") Optional<String> email,
                               @RequestParam("password") Optional<String> password) throws ServletException {

        Principal principal = request.getUserPrincipal();
        Optional<User> user = userRepository.findByEmail(principal.getName());
        System.out.println("COPIARPEGAR");
        if (user.get().getRole() == Role.ROLE_TEACHER) {
            Teacher teacher = teacherService.getTeacherByEmail(principal.getName());
            firstName.ifPresent(teacher::setFirstName);
            lastName.ifPresent(teacher::setLastName);
            email.ifPresent(teacher::setEmail);
            password.ifPresent(teacher::setPassword);
            teacherRepository.save(teacher);
        }else if (user.get().getRole() == Role.ROLE_STUDENT) {
            Student student = studentService.getStudentByEmail(principal.getName());
            firstName.ifPresent(student::setFirstName);
            lastName.ifPresent(student::setLastName);
            email.ifPresent(student::setEmail);
            password.ifPresent(student::setPassword);
            studentRepository.save(student);
        }
        return showLogout();
    }
}
