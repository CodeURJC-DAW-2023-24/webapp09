package es.codeurjc.helloworldvscode.Controller;

import es.codeurjc.helloworldvscode.Entitys.*;
import es.codeurjc.helloworldvscode.repository.StudentRepository;
import es.codeurjc.helloworldvscode.repository.SubjectRepository;
import es.codeurjc.helloworldvscode.repository.TeacherRepository;
import es.codeurjc.helloworldvscode.repository.UserRepository;
import es.codeurjc.helloworldvscode.services.AdminService;
import es.codeurjc.helloworldvscode.services.StudentService;
import es.codeurjc.helloworldvscode.services.SubjectService;
import es.codeurjc.helloworldvscode.services.TeacherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import java.util.stream.Collectors;


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

    @Autowired
    private AdminService adminService;


    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/logout")
    public String showLogout() {
        // Invalidar la sesión del usuario
        return "redirect:/login"; // Redireccionar a la página de login con un mensaje de logout
    }

    @GetMapping("/subjects_registereduser")
    public ModelAndView showSubjects(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        ModelAndView modelAndView = new ModelAndView("subjects_registereduser");
        Optional<User> user = userRepository.findByEmail(principal.getName());
        if (user.get().getRoles().contains("TEACHER")) {
            modelAndView.addObject("isStudent", false);
            Teacher teacher = teacherService.getTeacherByEmail(principal.getName());
            modelAndView.addObject("user", teacher);
            
        } else if (user.get().getRoles().contains("STUDENT")){
            modelAndView.addObject("isStudent", true);
            Student student = studentService.getStudentByEmail(principal.getName());
            List<Subject> recommendedSubjects = subjectService.recommendSubjects(student);
            modelAndView.addObject("user", student);
            modelAndView.addObject("recommendedSubjects", recommendedSubjects);
        } else if (user.get().getRoles().contains("ADMIN")){
            modelAndView.addObject("isStudent", false);
            Admin admin = adminService.getAdminByEmail(principal.getName());
            modelAndView.addObject("user", admin);

        }
        return modelAndView;
    }
    
    @GetMapping("/redirection/{subjectId}")
    public String redirectURL(HttpServletRequest request, @PathVariable Long subjectId) {
        Principal principal = request.getUserPrincipal();
        Optional<User> user = userRepository.findByEmail(principal.getName());
        String url = "";
        if (user.get().getRoles().contains("STUDENT")){
            url = "/subject_onesubj_student/"+ subjectId;
        } else if (user.get().getRoles().contains("TEACHER")){
            url = "/teachers/subject/" + subjectId + "/general-information";
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

        Principal principal = request.getUserPrincipal();
        Optional<User> userOptional = userRepository.findByEmail(principal.getName());
    
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            String firstName = user.getFirstName();
            model.addAttribute("firstName", firstName);

            String lastName = user.getLastName();
            model.addAttribute("lastName", lastName);

            String email = user.getEmail();
            model.addAttribute("email", email);
        }
        ModelAndView modelAndView = new ModelAndView("profile");
        model.addAttribute("username", request.getUserPrincipal().getName());
        String role = "UNKNOWN"; // Por defecto
        if (request.isUserInRole("STUDENT")) {
            role = "Student";
         } else if (request.isUserInRole("TEACHER")) {
             role = "Teacher";
         }
         

        
        List<Subject> subjects = new ArrayList<>();
        String username = request.getUserPrincipal().getName();
        Optional<Student> student = studentRepository.findByEmail(username);
        Optional<Teacher> teacher = teacherRepository.findByEmail(username);
        if (request.isUserInRole("STUDENT")) {
            // Si se encuentra un estudiante, obtener las asignaturas asociadas
            role = "Student";
            subjects = studentService.findSubjectsByStudentId(student.get().getId());
            modelAndView.addObject("subjects", subjects);
        } else if (request.isUserInRole("TEACHER")) {
            // Si se encuentra un profesor, obtener las asignaturas asociadas
            role = "Teacher";
            subjects = teacherService.findSubjectsByTeacherId(teacher.get().getId());
            modelAndView.addObject("subjects", subjects);
        }else if (request.isUserInRole("ADMIN")) {
            // Si se encuentra un profesor, obtener las asignaturas asociadas
            role = "Admin";
        }

        model.addAttribute("role", role);

        // Crear el modelo y agregar las asignaturas
        return modelAndView;
    }

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            model.addAttribute("logged", true);
        } else {
            model.addAttribute("logged", false);
        }
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
        if (user.isPresent()) {
        User currentUser = user.get();
        currentUser.setFirstName(firstName.orElse(currentUser.getFirstName()));
        currentUser.setLastName(lastName.orElse(currentUser.getLastName()));
        currentUser.setEmail(email.orElse(currentUser.getEmail()));
        currentUser.setPassword(password.orElse(currentUser.getPassword()));
        userRepository.save(currentUser);

        List<GrantedAuthority> authorities = currentUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(currentUser.getEmail(), currentUser.getPassword(), authorities)
        );
    }
        return "redirect:/profile";
    }
}
