package es.codeurjc.helloworldvscode.Controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
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

import es.codeurjc.helloworldvscode.Entitys.Student;
import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.Entitys.Teacher;
import es.codeurjc.helloworldvscode.repository.StudentRepository;
import es.codeurjc.helloworldvscode.repository.SubjectRepository;
import es.codeurjc.helloworldvscode.repository.TeacherRepository;
import es.codeurjc.helloworldvscode.services.SubjectService;

@RestController
@RequestMapping("/admins/" )
public class AdminController {

	// REPOSITORY //
	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private SubjectRepository subjectRepository;


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

	@GetMapping("/subject")
	public ModelAndView showSubjects(@RequestParam Long subjectId) {
		ModelAndView modelAndView = new ModelAndView();
		Subject subject = subjectRepository.findById(subjectId).get();
		ArrayList<Teacher> teachers = teacherRepository.findAllBySubjectsId(subjectId);
		
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
			
			response.sendRedirect("/admins/subject?subjectId="+subjectId);
		}else{
			response.sendRedirect("/error");
		}
	}


	@GetMapping("/subject/delete")
	public void deleteOneSubjects(@RequestParam Long subjectId, HttpServletResponse response) throws IOException {

		subjectService.deleteSubjectId(subjectId);
	
		response.sendRedirect("/");

	}



	////////////////////
	// DELETE TEACHER //
	////////////////////

	@SuppressWarnings("null")
	@GetMapping("/subject/{subjectId}/general-information/delete")
	public void deleteStudent(@PathVariable Long subjectId,  @RequestParam(required = false) Long teacherId, HttpServletResponse response) throws IOException {

		Optional<Subject> s = subjectRepository.findById(subjectId);

		if (s.isPresent()) {

			if(teacherId!=null){
				Optional<Teacher> te = teacherRepository.findById(teacherId);
				te.get().deleteSubjectId(subjectId);

				s.get().deleteTeacherId(teacherId);

				teacherRepository.save(te.get());
				subjectRepository.save(s.get());
			}

		}

		response.sendRedirect("/admins/subject?subjectId="+subjectId);

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

			modelAndView.addObject("rol", "admins");
			modelAndView.addObject("user", "teacher");
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

					response.sendRedirect("/admins/subject?subjectId="+subjectId);
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

					response.sendRedirect("/admins/subject?subjectId="+subjectId);
				} else { // if not exists
					response.sendRedirect("/admins/subject/"+subjectId+"/add-teacher?addorcreate="+addorcreate+"&info=false");
				}
			}
		}else{
			response.sendRedirect("/error");
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

		//Subject s = subjectRepository.findByName(nameSubject).get();
		boolean a = subjectService.isNameUnique(nameSubject);
		if (subjectService.isNameUnique(nameSubject)){
			Subject subject = new Subject(nameSubject,gender,allInfo,description);
	
			Optional<Teacher> existingTeacher = teacherRepository.findByEmail(teacherEmail);
			
			Teacher teacher = existingTeacher.get();
	
			subject.setOneTeacher(teacher);
			subjectRepository.save(subject);
	
			
			response.sendRedirect("/");
			
		}else{// Create new subject
			response.sendRedirect("/error");	
		}
		
	}

}
