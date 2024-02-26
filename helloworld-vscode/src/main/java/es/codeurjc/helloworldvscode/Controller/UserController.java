package es.codeurjc.helloworldvscode.Controller;

import es.codeurjc.helloworldvscode.Entitys.Student;
import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.Entitys.Teacher;
import es.codeurjc.helloworldvscode.Entitys.User;
import es.codeurjc.helloworldvscode.repository.StudentRepository;
import es.codeurjc.helloworldvscode.repository.SubjectRepository;
import es.codeurjc.helloworldvscode.repository.TeacherRepository;
import es.codeurjc.helloworldvscode.repository.UserRepository;
import es.codeurjc.helloworldvscode.services.StudentService;
import es.codeurjc.helloworldvscode.services.TeacherService;
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
    private StudentService studentService;
    @Autowired 
    private TeacherService teacherService;
    
    @GetMapping("/login")public String showLogin() {
        return "login";
    }
    /*
    @RequestMapping("/login")
    public String login(HttpServletRequest request,
                        Model model,
                        @RequestParam String email,
                        @RequestParam String password){
        //comprobaciones
        return "login";
    }*/
    @RequestMapping("/loginerror")
    public String loginerror() {
        return "loginerror";
    }

    @GetMapping("/sign-up")public String showSignup() {
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

        // Aquí podrías incluir validaciones, por ejemplo, verificar que los correos y contraseñas coincidan
        if(!email.equals(confirmEmail)) {
            model.addAttribute("error", "Emails do not match!");
            return "signup"; // Vuelve a la página de registro si hay un error
        }
        if(!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match!");
            return "signup"; // Vuelve a la página de registro si hay un error
        }
        // Crear y guardar el nuevo usuario
        Student newStudent = new Student(firstName, lastName, email,password);
        studentRepository.save(newStudent);
        return "redirect:/login";
    }

    @GetMapping("/profile")
	public ModelAndView personalArea(Model model, HttpServletRequest request) {
		model.addAttribute("username", request.getUserPrincipal().getName());
    	String role = "UNKNOWN"; // Por defecto
        if (request.isUserInRole("STUDENT")) {
            role = "STUDENT";
        } else if (request.isUserInRole("TEACHER")) {
            role = "TEACHER";
        } else if (request.isUserInRole("ADMIN")) {
            role = "ADMIN";
        }

        model.addAttribute("role", role); 
        //sse podria mostrar el email tambien
        List<Subject> subjects = new ArrayList<>();
        String username = request.getUserPrincipal().getName();
        Optional<User> student = studentRepository.findByFirstName(username);
        Optional<User> teacher = teacherRepository.findByFirstName(username);
        if (student.isPresent()) {
            // Si se encuentra un estudiante, obtener las asignaturas asociadas
            subjects = studentService.findSubjectsByStudentId(student.get().getId());
        } else if (teacher.isPresent()) {
            // Si se encuentra un profesor, obtener las asignaturas asociadas
            subjects = teacherService.findSubjectsByTeacherId(teacher.get().getId());
        }

        // Crear el modelo y agregar las asignaturas
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("subjects", subjects);
        return modelAndView;
	}

    @GetMapping("/editProfile")
    public String showEditProfile() {
        return "editProfile";
    }
}
