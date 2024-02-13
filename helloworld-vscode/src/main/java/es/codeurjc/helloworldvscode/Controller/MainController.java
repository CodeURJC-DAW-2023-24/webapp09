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

}