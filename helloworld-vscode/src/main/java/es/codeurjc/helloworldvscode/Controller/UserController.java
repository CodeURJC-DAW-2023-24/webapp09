package es.codeurjc.helloworldvscode.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;



@Controller
public class UserController { 

    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        return "login";
    }
    @RequestMapping("/loginerror")
	public String loginerror() {
		return "loginerror";
	}
    @RequestMapping("/sign-up")
    public String signup(HttpServletRequest request){
        return "signup";
    }
}