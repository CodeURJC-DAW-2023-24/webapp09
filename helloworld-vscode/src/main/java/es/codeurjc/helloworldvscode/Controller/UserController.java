package es.codeurjc.helloworldvscode.Controller;

import es.codeurjc.helloworldvscode.Entitys.Student;
import es.codeurjc.helloworldvscode.Entitys.Teacher;
import es.codeurjc.helloworldvscode.repository.StudentRepository;
import es.codeurjc.helloworldvscode.repository.TeacherRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.sql.Blob;

@Controller
public class UserController {
    @Autowired private StudentRepository studentRepository;
    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        return "login";


    }
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
}
