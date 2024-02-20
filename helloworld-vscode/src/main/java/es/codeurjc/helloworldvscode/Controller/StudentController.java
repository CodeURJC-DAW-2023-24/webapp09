
package es.codeurjc.helloworldvscode.Controller;
import es.codeurjc.helloworldvscode.Entitys.Student;
import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.Services.StudentService;
import es.codeurjc.helloworldvscode.repository.StudentRepository;
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


    @GetMapping("/subjects_registereduser/{studentId}")public ModelAndView showStudentSubjects(@PathVariable Long studentId) {
        List<Subject> subjects = studentService.findSubjectsByStudentId(studentId);
        ModelAndView modelAndView = new ModelAndView("subjects_registereduser");
        modelAndView.addObject("subjects", subjects);
        Student student = studentService.getStudentById(studentId);
        modelAndView.addObject("student", student);
        return modelAndView;

    }

    @GetMapping("subject_onesubj_student")public ModelAndView showStudentSubject() {
        ModelAndView view=new ModelAndView("subject_onesubj_student");
        return view ;
    }


}
