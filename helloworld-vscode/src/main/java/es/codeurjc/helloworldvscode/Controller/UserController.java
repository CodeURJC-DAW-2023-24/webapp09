package es.codeurjc.helloworldvscode.Controller;

import es.codeurjc.helloworldvscode.Entitys.Teacher;
import es.codeurjc.helloworldvscode.repository.TeacherRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.sql.Blob;

@Controller
public class UserController { 
    @Autowired private TeacherRepository teacherRepository;
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
            Teacher newTeacher = new Teacher(firstName, lastName, email,password, null);

            // Suponiendo que "USER" es un rol o algo similar que tu entidad User pueda almacenar


            teacherRepository.save(newTeacher);

            // Redirige a la página de área personal, no uses "redirect:" si quieres pasar datos a través del modelo
            return "login";

    }


}