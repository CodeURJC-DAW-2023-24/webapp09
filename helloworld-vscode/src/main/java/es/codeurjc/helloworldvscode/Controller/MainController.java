package es.codeurjc.helloworldvscode.Controller;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import java.util.Collection;

import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.Entitys.Teacher;
import es.codeurjc.helloworldvscode.repository.SubjectRepository;
import es.codeurjc.helloworldvscode.repository.TeacherRepository;
import es.codeurjc.helloworldvscode.services.SubjectService;

@Controller
public class MainController { 

    @Autowired
	SubjectRepository subjectsList;

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model){
        model.addAttribute("subjects",subjectsList.findAll());
        return "main_page";
    }

    @RequestMapping("/subject") // LLEVARÁ UN ID DE ASIGNATURA 
    public String subject_student(HttpServletRequest request){
        return "subject_onesubj_student";
    }

    @RequestMapping("/subject-info") // LLEVARÁ UN ID DE ASIGNATURA 
    public String subject_info(HttpServletRequest request){
        return "subject_info";
    }

    @RequestMapping("/subjects") // LLEVARÁ UN ID DE USER 
    public String subjects_registereduser(HttpServletRequest request){
        return "subjects_registereduser";
    }

    @RequestMapping("/profile") // LLEVARÁ UN ID DE USER
    public String profile(HttpServletRequest request){
        return "profile";
    }

    @RequestMapping("/editProfile") // LLEVARÁ UN ID DE USER
    public String editProfile(HttpServletRequest request){
        return "editProfile";
    }

    @RequestMapping("/edit-profile")
    public String editProfile() {
        return "editProfile";
    }

    @RequestMapping("/subject-one-subj-admin")
    public String subjectOneSubjAdmin() {
        return "subject_onesubj_admin";
    }
    
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

}