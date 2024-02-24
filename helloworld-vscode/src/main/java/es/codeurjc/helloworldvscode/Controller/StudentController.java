
package es.codeurjc.helloworldvscode.Controller;
import es.codeurjc.helloworldvscode.Entitys.Student;
import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.services.*;
import es.codeurjc.helloworldvscode.repository.StudentRepository;
import es.codeurjc.helloworldvscode.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/students/")

public class StudentController {

    @Autowired
    StudentService studentService;
    @Autowired
    SubjectService subjectService;


    @GetMapping("/subjects_registereduser/{studentId}")public ModelAndView showStudentSubjects(@PathVariable Long studentId) {
        List<Subject> subjects = studentService.findSubjectsByStudentId(studentId);
        ModelAndView modelAndView = new ModelAndView("subjects_registereduser");
        modelAndView.addObject("subjects", subjects);
        Student student = studentService.getStudentById(studentId);
        modelAndView.addObject("student", student);
        return modelAndView;
    }

    @GetMapping("/subject_onesubj_student/{studentId}/{subjectId}")
    public ModelAndView showSubjectDetails(@PathVariable Long studentId, @PathVariable Long subjectId) {
        ModelAndView modelAndView = new ModelAndView("subject_onesubj_student"); // Asume que tienes una vista con este nombre
        Subject subject = subjectService.getSubjectById(subjectId); // Asume que tienes un servicio para buscar asignaturas por ID
        Student student = studentService.getStudentById(studentId); // Reutilización del servicio existente

        modelAndView.addObject("subject", subject);
        modelAndView.addObject("student", student);
        return modelAndView;
    }
}
