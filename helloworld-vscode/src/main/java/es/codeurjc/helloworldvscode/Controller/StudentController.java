
package es.codeurjc.helloworldvscode.Controller;
import es.codeurjc.helloworldvscode.Entitys.*;
import es.codeurjc.helloworldvscode.repository.ForumRepository;
import es.codeurjc.helloworldvscode.repository.SubjectRepository;
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

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    ForumRepository forumRepository;


    @GetMapping("/subject_onesubj_student/{subjectId}")
    public ModelAndView showSubjectDetails(HttpServletRequest request, @PathVariable Long subjectId) {
        Principal principal = request.getUserPrincipal();
        ModelAndView modelAndView = new ModelAndView("subject_onesubj_student");

        boolean isStudent = request.isUserInRole("STUDENT");
        if (isStudent) {
            Student student = studentService.getStudentByName(principal.getName());
            modelAndView.addObject("user", student);
            
            Long studentId = student.getId();
            Subject subject = subjectService.getSubjectById(subjectId);
            List<ExamStudent> examStudents =examService.findExamStudentsByStudentAndSubject(studentId, subjectId);
            List<Forum> forums=forumRepository.findBySubjectId(subjectId);
            modelAndView.addObject("exams", subject.getExams());
            modelAndView.addObject("subject", subject);
            modelAndView.addObject("student", student);
            modelAndView.addObject("examStudents", examStudents);
            modelAndView.addObject("forums", forums);
        }
        return modelAndView;
    }

    @PostMapping("/subject_onesubj_student/{subjectId}")
    public String showSignup(HttpServletRequest request,
                             Model model,
                             @RequestParam String comment,@PathVariable Long subjectId) {
        Subject subject = subjectService.getSubjectById(subjectId);
        Principal principal = request.getUserPrincipal();


        Forum forum=new Forum(principal.getName(), comment, subject);
        subject.getForums().add(forum);

        subjectRepository.save(subject);
        forumRepository.save(forum);



        return ("redirect:/subject_onesubj_student/"+subject.getId());
    }
}
