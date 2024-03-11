
package es.codeurjc.helloworldvscode.Controller;
import es.codeurjc.helloworldvscode.Entitys.*;
import es.codeurjc.helloworldvscode.repository.ExamStudentRepository;
import es.codeurjc.helloworldvscode.repository.ForumRepository;
import es.codeurjc.helloworldvscode.repository.SubjectRepository;
import es.codeurjc.helloworldvscode.services.*;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    ExamStudentRepository examstudentRepository;
    @Autowired
    ExamStudentService examStudentService;

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            model.addAttribute("logged", true);
        } else {
            model.addAttribute("logged", false);
        }
    }

    @GetMapping("/subject_onesubj_student/{subjectId}")
    public ModelAndView showSubjectDetails(HttpServletRequest request, @PathVariable Long subjectId) {
        Principal principal = request.getUserPrincipal();
        ModelAndView modelAndView = new ModelAndView("subject_onesubj_student");

        boolean isStudent = request.isUserInRole("STUDENT");
        if (isStudent) {
            Student student = studentService.getStudentByEmail(principal.getName());
            
            Subject subject = subjectService.getSubjectById(subjectId);
            List<ExamStudent> examStudents = student.getExamStudents();
            List<Forum> forums = subject.getForums();
            List<Exam> exams = examStudentService.getIncompleteExamsStudent(student,subject);

            //show only the empty exams
            modelAndView.addObject("description", subject.getDescription());
            modelAndView.addObject("user", student);
            modelAndView.addObject("exams", exams);
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

        Forum forum = new Forum(principal.getName(), comment, subject);
        forumRepository.save(forum);

        return ("redirect:/subject_onesubj_student/"+subject.getId());
    }

    @GetMapping("/subject_onesubj_student/{subjectId}/download")
	public ResponseEntity<byte[]> downloadFile(@PathVariable Long subjectId, @RequestParam Long examId) {
		Exam exam = examService.getFile(examId);

    	return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + exam.getName() + "." + exam.getType() +"\"")
        .body(exam.getData());
  	}
    
    @PostMapping("/subject_onesubj_student/{subjectId}/upload")
    public String uploadFile(HttpServletRequest request, @PathVariable("subjectId") Long subjectId, @RequestParam Long examId, @RequestParam("file") MultipartFile file) throws IOException {
        Principal principal = request.getUserPrincipal();
        Student student = studentService.getStudentByEmail(principal.getName());
        Optional<Exam> exam = examService.findById(examId);


        examStudentService.store(file, exam.get(), 0, student);
        
        return ("redirect:/subject_onesubj_student/"+subjectId);
    }
}
