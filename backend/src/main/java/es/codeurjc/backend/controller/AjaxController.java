package es.codeurjc.backend.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.backend.model.Subject;
import es.codeurjc.backend.services.SubjectService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AjaxController {

    @Autowired
	private SubjectService subjectService;
    

    @ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			model.addAttribute("logged", true);
		} else {
			model.addAttribute("logged", false);
		}
	}


    @GetMapping("/moreSubjectsAdmin")
	public String getMoreSubjectsAdmin(Model model, @RequestParam int page) {
		Page<Subject> moreSubjects = subjectService.getAllPage(PageRequest.of(page, 3));
		if (moreSubjects == null) {
			return null;
		} else {
			model.addAttribute("moreSubjects", moreSubjects);
			return "moreSubjectsAdmin";
		}
	}
}
