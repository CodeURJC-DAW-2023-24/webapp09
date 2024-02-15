package es.codeurjc.helloworldvscode.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;




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