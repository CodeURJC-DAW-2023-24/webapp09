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

    @RequestMapping("/edit-profile")
    public String editProfile() {
        return "editProfile";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/main-page")
    public String mainPage() {
        return "main_page";
    }

    @RequestMapping("/profile")
    public String profile() {
        return "profile";
    }

    @RequestMapping("/signup")
    public String signup() {
        return "signup";
    }

    @RequestMapping("/subjects-registered-user")
    public String subjectsRegisteredUser() {
        return "subjects_registereduser";
    }

    @RequestMapping("/subject-info")
    public String subjectInfo() {
        return "subject_info";
    }

    @RequestMapping("/subject-one-subj-admin")
    public String subjectOneSubjAdmin() {
        return "subject_onesubj_admin";
    }

    @RequestMapping("/subject-one-subj-student")
    public String subjectOneSubjStudent() {
        return "subject_onesubj_student";
    }

    @RequestMapping("/subject-one-subj-teacher")
    public String subjectOneSubjTeacher() {
        return "subject_onesubj_teacher";
    }


}