package es.codeurjc.helloworldvscode.Controller;

import es.codeurjc.helloworldvscode.Entitys.Student;
import es.codeurjc.helloworldvscode.Entitys.Teacher;
import es.codeurjc.helloworldvscode.Entitys.User;
import es.codeurjc.helloworldvscode.repository.StudentRepository;
import es.codeurjc.helloworldvscode.repository.TeacherRepository;
import es.codeurjc.helloworldvscode.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.security.Principal;
import java.sql.Blob;


@Controller
public class UserController {
    @Autowired private StudentRepository studentRepository;
    @Autowired
	//private UserService userService;

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
	public String personalArea(Model model, HttpServletRequest request) {
		//model.addAttribute("username", request.getUserPrincipal().getName());
    	//model.addAttribute("admin", request.isUserInRole("ADMIN")); 
		return "profile";
	}

    @GetMapping("/editProfile")
    public String showEditProfile() {
        return "editProfile";
    }
}
