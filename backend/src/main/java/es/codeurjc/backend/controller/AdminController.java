package es.codeurjc.backend.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import es.codeurjc.backend.model.Subject;
import es.codeurjc.backend.model.Teacher;
import es.codeurjc.backend.services.SubjectService;
import es.codeurjc.backend.services.TeacherService;

@RestController
@RequestMapping("/admins/")
public class AdminController {

	// Services //

	@Autowired
	private SubjectService subjectService;
	@Autowired
	private TeacherService teacherService;

	@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			model.addAttribute("logged", true);
		} else {
			model.addAttribute("logged", false);
		}
	}

	@GetMapping("/subject")
	public ModelAndView showSubjects(@RequestParam Long subjectId) {
		ModelAndView modelAndView = new ModelAndView();
		Subject subject = subjectService.getSubjectById(subjectId);
		List<Teacher> teachers = teacherService.getAllBySubjectsId(subjectId);

		modelAndView.setViewName("subject_onesubj_admin");

		modelAndView.addObject("description", subject.getDescription());
		modelAndView.addObject("allInfo", subject.getAllInfo());

		modelAndView.addObject("teachers", teachers);
		modelAndView.addObject("name", subject.getName());
		modelAndView.addObject("subjectId", subjectId);

		modelAndView.addObject("rol", "admins");
		modelAndView.addObject("user", "teacher");

		return modelAndView;
	}

	@PostMapping("/subject")
	public void subjectOneSubjAdmin(@RequestParam Long subjectId, HttpServletResponse response,
			Model model,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) String allInfo) throws IOException {

		Subject subject = subjectService.getSubjectById(subjectId);

		if (!subject.getDescription().equals(description)) {

			if (description != null) {
				subject.setDescription(description);
			}
			if (allInfo != null) {
				subject.setAllInfo(allInfo);
			}

			subjectService.setSubject(subject);
		}

		response.sendRedirect("/admins/subject?subjectId=" + subjectId);

	}

	@GetMapping("/subject/delete")
	public void deleteOneSubjects(@RequestParam Long subjectId, HttpServletResponse response) throws IOException {

		subjectService.deleteSubjectId(subjectId);

		response.sendRedirect("/");

	}

	////////////////////
	// DELETE TEACHER //
	////////////////////

	@GetMapping("/subject/{subjectId}/general-information/delete")
	public void deleteStudent(@PathVariable Long subjectId, @RequestParam(required = false) Long teacherId,
			HttpServletResponse response) throws IOException {

		Subject subject = subjectService.getById(subjectId);

		if (teacherId != null) {
			Teacher teacher = teacherService.getById(teacherId);
			teacher.deleteSubjectId(subjectId);

			subject.deleteTeacherId(teacherId);

			teacherService.setTeacher(teacher);
			subjectService.setSubject(subject);
		}

		response.sendRedirect("/admins/subject?subjectId=" + subjectId);

	}

	//////////////////
	// ADD TEACHER //
	//////////////////

	@GetMapping("/subject/{subjectId}/add-teacher")
	public ModelAndView showCreateTeacher(@PathVariable Long subjectId, @RequestParam String addorcreate,
			@RequestParam(required = false) String info) {
		ModelAndView modelAndView = new ModelAndView();

		Subject subject = subjectService.getById(subjectId);

		if (info.equals("true")) {
			modelAndView.addObject("error", "");
		} else if (info.equals("false")) {
			modelAndView.addObject("error", "Teacher not found");
		} else {
			modelAndView.addObject("error", "Incomplete information");
		}

		if (addorcreate != null) {
			if ("new".equals(addorcreate)) {
				modelAndView.setViewName("add_teacher_new");

			} else {
				modelAndView.setViewName("add_teacher_notnew");

			}
		} else {
			modelAndView.setViewName("add_teacher_notnew");

		}

		modelAndView.addObject("rol", "admins");
		modelAndView.addObject("user", "teacher");
		modelAndView.addObject("name", subject.getName());

		return modelAndView;
	}

	@PostMapping("/subject/{subjectId}/add-teacher")
	public void showCreateTeacher(@RequestParam String addorcreate, @PathVariable Long subjectId,
			@RequestParam String info, HttpServletResponse response,
			HttpServletRequest request,
			Model model,
			@RequestParam(required = false) String firstName,
			@RequestParam(required = false) String lastName,
			@RequestParam String email,
			@RequestParam(required = false) String password) throws IOException {

		// Create new student
		if (password == null) {
			password = "1";
		}
		Teacher teacher = new Teacher(firstName, lastName, email, password);

		// Find subject by name
		Subject subject = subjectService.getById(subjectId);

		// if need to create a new student
		if ("new".equals(addorcreate)) {
			if (firstName != null && password != null && lastName != null) { // if the data is complete
				teacherService.setTeacher(teacher);
				subject.setOneTeacher(teacher);
				subjectService.setSubject(subject);

				response.sendRedirect("/admins/subject?subjectId=" + subjectId);
			} else { // if the data isn't complete
				response.sendRedirect("/admins/subject/" + subjectId + "/add-teacher?addorcreate=" + addorcreate
						+ "&info=incomplete");
			}

			// if the student is created
		} else {
			Optional<Teacher> existingT = teacherService.getOptionalByEmail(email); // find student for updated it

			if (existingT.isPresent()) { // if the student exists
				teacher = existingT.get();
				subject.setOneTeacher(teacher);
				subjectService.setSubject(subject);

				response.sendRedirect("/admins/subject?subjectId=" + subjectId);
			} else { // if not exists
				response.sendRedirect(
						"/admins/subject/" + subjectId + "/add-teacher?addorcreate=" + addorcreate + "&info=false");
			}
		}

	}

	@GetMapping("/add-subject")
	public ModelAndView createSubject() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("add_subject");

		return modelAndView;
	}

	@PostMapping("/add-subject")
	public void createSubject(
			HttpServletResponse response,
			@RequestParam(required = false) String nameSubject,
			@RequestParam(required = false) String gender,
			@RequestParam(required = false) String allInfo,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) String teacherEmail) throws IOException {

		if (subjectService.isNameUnique(nameSubject)) {
			Subject subject = new Subject(nameSubject, gender, allInfo, description);

			Teacher existingTeacher = teacherService.getByEmail(teacherEmail);

			subject.setOneTeacher(existingTeacher);
			subjectService.setSubject(subject);

			response.sendRedirect("/");

		} else {// Create new subject
			response.sendRedirect("/error");
		}

	}

}
