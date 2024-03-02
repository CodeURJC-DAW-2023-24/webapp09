package es.codeurjc.helloworldvscode.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import es.codeurjc.helloworldvscode.Entitys.Student;
import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.Entitys.Teacher;
import es.codeurjc.helloworldvscode.repository.StudentRepository;
import es.codeurjc.helloworldvscode.repository.SubjectRepository;
import es.codeurjc.helloworldvscode.repository.TeacherRepository;

@RestController
@RequestMapping("/admins/")
public class AdminController {

	// REPOSITORY //
	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private StudentRepository studentRepository;

	////////////////////////
	// UPDATE DESCRIPTION //
	////////////////////////
	@GetMapping("/subject/{subjectId}/general-information")
	public ModelAndView subjectOneSubjAdmin(@PathVariable Long subjectId) {

		ModelAndView modelAndView = new ModelAndView();
		Optional<Subject> s = subjectRepository.findById(subjectId);

		if (s.isPresent()) {
			String description = s.get().getDescription();
			String allInfo = s.get().getAllInfo();
			String name = s.get().getName();

			ArrayList<Student> students = (ArrayList<Student>) studentRepository.findAllBySubjectsId(subjectId);
			ArrayList<Teacher> teachers = (ArrayList<Teacher>) teacherRepository.findAllBySubjectsId(subjectId);

			modelAndView.setViewName("general_information");

			modelAndView.addObject("students", students);
			modelAndView.addObject("teachers", teachers);
			modelAndView.addObject("description", description);
			modelAndView.addObject("allInfo", allInfo);
			modelAndView.addObject("name", name);

		} else {
			modelAndView.addObject("error", "Subject not found");
			modelAndView.setViewName("error_view");
		}

		return modelAndView;
	}


	@PostMapping("/subject/{subjectId}/general-information")
	public void subjectOneSubjAdmin(@PathVariable Long subjectId, HttpServletResponse response,
			Model model,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) String allInfo) throws IOException {

		Optional<Subject> s = subjectRepository.findById(subjectId);

		if (s.isPresent()) {
			Subject subject = s.get();
			if (!subject.getDescription().equals(description)) {

				if(description!=null){
					subject.setDescription(description);
				}
				if(allInfo!=null){
					subject.setAllInfo(allInfo);
				}

				subjectRepository.save(subject);
			}

			response.sendRedirect("/admins/subject/"+subjectId+"/general-information");
		}else{
			response.sendRedirect("/error");
		}
	}



	///////////////////////////////
	// DELETE TEACHER OR STUDENT //
	///////////////////////////////

	@SuppressWarnings("null")
	@GetMapping("/subject/{subjectId}/general-information/delete")
	public void deleteStudent(@PathVariable Long subjectId, @RequestParam(required = false) Long student, @RequestParam(required = false) Long teacher, HttpServletResponse response) throws IOException {

		Optional<Subject> s = subjectRepository.findById(subjectId);

		if (s.isPresent()) {

			if(student!=null){
				Optional<Student> st = studentRepository.findById(student);
				st.get().deleteSubjectId(subjectId);

				s.get().deleteStudentId(student);

				studentRepository.save(st.get());
				subjectRepository.save(s.get());

			}else if(teacher!=null){
				Optional<Teacher> te = teacherRepository.findById(teacher);
				te.get().deleteSubjectId(subjectId);

				s.get().deleteTeacherId(teacher);

				teacherRepository.save(te.get());
				subjectRepository.save(s.get());
			}

		}

		response.sendRedirect("/admins/subject/"+subjectId+"/general-information");

	}



	//////////////////
	// ADD TEACHER //
	//////////////////

	@GetMapping("/subject/{subjectId}/add-teacher")
	public ModelAndView showCreateTeacher(@PathVariable Long subjectId, @RequestParam String addorcreate, @RequestParam(required = false) String info) {
		ModelAndView modelAndView = new ModelAndView();

		Optional<Subject> s = subjectRepository.findById(subjectId);

		if (s.isPresent()) {

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

			modelAndView.addObject("rol", "teacher"); // add description
			modelAndView.addObject("name", s.get().getName());


		} else {
			modelAndView.addObject("error", "Subject not found");
			modelAndView.setViewName("loginerror");
		}

		return modelAndView;
	}

	@PostMapping("/subject/{subjectId}/add-teacher")
	public void showCreateTeacher(@RequestParam String addorcreate, @PathVariable Long subjectId, @RequestParam String info, HttpServletResponse response,
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
		Optional<Subject> s = subjectRepository.findById(subjectId);

		if (s.isPresent()) { // if the subject exists in the db
			Subject subject = s.get();

			// if need to create a new student
			if ("new".equals(addorcreate)) { 
				if (firstName != null && password != null && lastName != null) { // if the data is complete
					teacherRepository.save(teacher); 
					subject.setOneTeacher(teacher);
					subjectRepository.save(subject);

					response.sendRedirect("/admins/subject/"+subjectId+"/general-information");
				} else { // if the data isn't complete
					response.sendRedirect("/admins/subject/"+subjectId+"/add-teacher?addorcreate="+addorcreate+"&info=incomplete");
				}

			// if the student is created
			} else { 
				Optional<Teacher> existingT = teacherRepository.findByEmail(email); // find student for updated it

				if (existingT.isPresent()) { // if the student exists
					teacher = existingT.get(); 
					subject.setOneTeacher(teacher);
					subjectRepository.save(subject);

					response.sendRedirect("/admins/subject/"+subjectId+"/general-information");
				} else { // if not exists
					response.sendRedirect("/admins/subject/"+subjectId+"/add-teacher?addorcreate="+addorcreate+"&info=false");
				}
			}
		}else{
			response.sendRedirect("/error");
		}
	}

	//////////////////
	// ADD STUDENT //
	//////////////////

	@GetMapping("/subject/{subjectId}/add-student")
	public ModelAndView showCreateStudent(@PathVariable Long subjectId, @RequestParam String addorcreate, @RequestParam String info) {
		ModelAndView modelAndView = new ModelAndView();

		Optional<Subject> s = subjectRepository.findById(subjectId);

		if (s.isPresent()) {

			if (info.equals("true")) {
				modelAndView.addObject("error", "");
			} else if (info.equals("false")) {
				modelAndView.addObject("error", "Student not found");
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

			modelAndView.addObject("rol", "student"); // add description
			modelAndView.addObject("name", s.get().getName());


		} else {
			modelAndView.addObject("error", "Subject not found");
			modelAndView.setViewName("loginerror");
		}

		return modelAndView;
	}

	@PostMapping("/subject/{subjectId}/add-student")
	public void showCreateStudent(@RequestParam String addorcreate, @PathVariable Long subjectId, @RequestParam String info, HttpServletResponse response,
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
		Student student = new Student(firstName, lastName, email, password);

		// Find subject by name
		Optional<Subject> s = subjectRepository.findById(subjectId);

		if (s.isPresent()) { // if the subject exists in the db
			Subject subject = s.get();

			// if need to create a new student
			if ("new".equals(addorcreate)) { 
				if (firstName != null && password != null && lastName != null) { // if the data is complete
					studentRepository.save(student); 
					subject.setOneStudent(student);
					subjectRepository.save(subject);
					response.sendRedirect("/admins/subject/"+subjectId+"/general-information");
				} else { // if the data isn't complete
					response.sendRedirect("/subject/"+subjectId+"/add-student?addorcreate="+addorcreate+"&info=incomplete");
				}

			// if the student is created
			} else { 
				Optional<Student> existingEstudent = studentRepository.findByEmail(email); // find student for updated it

				if (existingEstudent.isPresent()) { // if the student exists
					student = existingEstudent.get(); 
					subject.setOneStudent(student);
					subjectRepository.save(subject);
					response.sendRedirect("/admins/subject/"+subjectId+"/general-information");
				} else { // if not exists
					response.sendRedirect("/admins/subject/"+subjectId+"/add-student?addorcreate="+addorcreate+"&info=false");
				}
			}
		}else{
			response.sendRedirect("/error");
		}
	}

}
