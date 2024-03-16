package es.codeurjc.backend.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.backend.model.Exam;
import es.codeurjc.backend.model.Student;
import es.codeurjc.backend.model.Subject;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.services.ExamService;
import es.codeurjc.backend.services.StudentService;
import es.codeurjc.backend.services.SubjectService;
import es.codeurjc.backend.services.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AjaxController {

	@Autowired
	private SubjectService subjectService;
	@Autowired
	private UserService userService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private ExamService examService;

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

	@GetMapping("/moreSubjectsMain")
	public String getMoreSubjectsMain(Model model, @RequestParam int page, HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			
			Optional<User> user = userService.getByEmail(principal.getName());

			//if the user is teacher
			if (user.get().getRoles().contains("TEACHER")) {

				Page<Subject> moreSubjects = subjectService.getAllPage(PageRequest.of(page, 3));

				if (moreSubjects == null) {
					return null;
				} else {
					model.addAttribute("moreSubjects", moreSubjects);
					return "moreSubjectsMain";
				}

				
			//if the user is student
			} else if(user.get().getRoles().contains("STUDENT")){

				Student student = studentService.getStudentByEmail(principal.getName());
				Page<Subject> moreSubjects = subjectService.getAllNotEnrolled(student.getSubjects(), PageRequest.of(0, 3));

				if (moreSubjects == null) {
					return null;
				} else {
					
					model.addAttribute("moreSubjects", moreSubjects);
					return "moreSubjectsMain";
				}
			}

		//if the user isnt logged in
		}else{
			Page<Subject> moreSubjects = subjectService.getAllPage(PageRequest.of(page, 3));
			
			if (moreSubjects == null) {
				return null;
			} else {
				model.addAttribute("moreSubjects", moreSubjects);
				return "moreSubjectsMain";
			}
		}

		return null;
	}


	@GetMapping("/teachers/subject/{subjectId}/type-exams/moreTeacherExam")
	public String getMoreTeacherExam(@PathVariable long subjectId, Model model, @RequestParam int page) {
		Page<Exam> moreExams = examService.getAllBySubjectIdPage(subjectId, PageRequest.of(page, 3));
		if (moreExams == null) {
			return null;
		} else {
			model.addAttribute("moreExams", moreExams);
			return "moreTeacherExams";
		}
	}
}
