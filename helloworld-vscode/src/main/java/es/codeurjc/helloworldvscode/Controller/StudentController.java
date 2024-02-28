
package es.codeurjc.helloworldvscode.Controller;
import es.codeurjc.helloworldvscode.Entitys.ExamStudent;
import es.codeurjc.helloworldvscode.Entitys.Student;
import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.services.*;
import jakarta.servlet.http.HttpServletRequest;
import es.codeurjc.helloworldvscode.repository.StudentRepository;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {

    @Autowired
    StudentService studentService;
    @Autowired
    SubjectService subjectService;

    @Autowired
    ExamService examService;

    @GetMapping("/subject_onesubj_student/{studentId}/{subjectId}")
    public ModelAndView showSubjectDetails(@PathVariable Long studentId, @PathVariable Long subjectId) {
        ModelAndView modelAndView = new ModelAndView("subject_onesubj_student"); // Asume que tienes una vista con este nombre
        Subject subject = subjectService.getSubjectById(subjectId); // Asume que tienes un servicio para buscar asignaturas por ID
        Student student = studentService.getStudentById(studentId); // Reutilización del servicio existente
        List<ExamStudent> examStudents =examService.findExamStudentsByStudentAndSubject(studentId, subjectId);
        modelAndView.addObject("subject", subject);
        modelAndView.addObject("student", student);
        modelAndView.addObject("examStudents", examStudents);
        return modelAndView;
    }
}
