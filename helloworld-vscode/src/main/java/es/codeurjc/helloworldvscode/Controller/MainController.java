package es.codeurjc.helloworldvscode.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;



@Controller
public class MainController { 

    @RequestMapping("/")
    public String index1(HttpServletRequest request){
        return "main_page";
    }
    @RequestMapping("/main_page")
    public String index(HttpServletRequest request){
        return "main_page";
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