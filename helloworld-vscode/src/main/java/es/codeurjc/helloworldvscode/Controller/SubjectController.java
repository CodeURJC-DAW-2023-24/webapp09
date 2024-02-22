package es.codeurjc.helloworldvscode.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.repository.SubjectRepository;
import es.codeurjc.helloworldvscode.services.SubjectService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class SubjectController {

    @Autowired
    SubjectRepository subjectsList;
    @Autowired
    SubjectService subjectService;

    @GetMapping("/")
    public ModelAndView showSubjects(Model model, HttpSession session, Pageable pageable) {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("main_page");

        // Configuración de la paginación para mostrar 5 elementos por página
        pageable = PageRequest.of(pageable.getPageNumber(), 5);

        Page<Subject> subjects = subjectService.findAll(pageable);

        model.addAttribute("subjects", subjects);
        model.addAttribute("hasPrev", subjects.hasPrevious());
        model.addAttribute("hasNext", subjects.hasNext());
        model.addAttribute("nextPage", subjects.getNumber() + 1);
        model.addAttribute("prevPage", subjects.getNumber() - 1);

        return modelView;
    }

    @GetMapping("/subject/{id}")
    public ModelAndView uniqueEvent(HttpServletRequest request, Model model, @PathVariable Long id) {
        ModelAndView modelView = new ModelAndView();
        if (subjectService.unique(id).isPresent()) {
            modelView.setViewName("subject_info");
            model.addAttribute("subject", subjectService.unique(id).get());
            return modelView;
        } else {
            modelView.setViewName("error");
            return modelView;
        }
    }
}
