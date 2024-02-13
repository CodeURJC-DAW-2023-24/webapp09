package es.codeurjc.helloworldvscode.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;



@Controller
public class MainController { 

    @RequestMapping("/")
    public String index(HttpServletRequest request){
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

}