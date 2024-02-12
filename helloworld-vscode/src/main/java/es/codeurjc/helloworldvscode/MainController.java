package es.codeurjc.helloworldvscode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class MainController { 

    @GetMapping("/")
    public String  main_page() {
        return "main_page";
    }

}