package es.codeurjc.helloworldvscode.Controller;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import es.codeurjc.helloworldvscode.Entitys.Exam;
import es.codeurjc.helloworldvscode.Entitys.ExamStudent;
import es.codeurjc.helloworldvscode.Entitys.Forum;
import es.codeurjc.helloworldvscode.Entitys.Student;
import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.Entitys.Teacher;
import es.codeurjc.helloworldvscode.repository.ExamRepository;
import es.codeurjc.helloworldvscode.repository.ExamStudentRepository;
import es.codeurjc.helloworldvscode.repository.ForumRepository;
import es.codeurjc.helloworldvscode.repository.StudentRepository;
import es.codeurjc.helloworldvscode.repository.SubjectRepository;
import es.codeurjc.helloworldvscode.repository.TeacherRepository;
import es.codeurjc.helloworldvscode.services.ExamService;
import es.codeurjc.helloworldvscode.services.ExamStudentService;

@RestController
@RequestMapping("/teachers/")
public class TeacherController {
	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private ExamRepository examRepository;

	@Autowired
	private ExamStudentRepository examStudentRepository;

	@Autowired
	private ForumRepository forumRepository;


	// SERVICES //
	@Autowired
	private ExamService examService;
	
	@Autowired
	private ExamStudentService examStudentService;


	////////////////////////
	// UPDATE DESCRIPTION //
	////////////////////////
	@GetMapping("/subject/{subjectId}/generalinformation")
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
			
		response.sendRedirect("/teachers/subject/"+subjectId+"/general-information");

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

					response.sendRedirect("/teachers/subject/"+subjectId+"/general-information");
				} else { // if the data isn't complete
					response.sendRedirect("/teachers/subject/"+subjectId+"/add-teacher?addorcreate="+addorcreate+"&info=incomplete");
				}

			// if the student is created
			} else { 
				Optional<Teacher> existingT = teacherRepository.findByEmail(email); // find student for updated it

				if (existingT.isPresent()) { // if the student exists
					teacher = existingT.get(); 
					subject.setOneTeacher(teacher);
					subjectRepository.save(subject);

					response.sendRedirect("/teachers/subject/"+subjectId+"/general-information");
				} else { // if not exists
					response.sendRedirect("/teachers/subject/"+subjectId+"/add-teacher?addorcreate="+addorcreate+"&info=false");
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
					response.sendRedirect("/teachers/subject/"+subjectId+"/general-information");
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
					response.sendRedirect("/teachers/subject/"+subjectId+"/general-information");
				} else { // if not exists
					response.sendRedirect("/teachers/subject/"+subjectId+"/add-student?addorcreate="+addorcreate+"&info=false");
				}
			}
		}else{
			response.sendRedirect("/error");
		}
	}

	//////////////////////////////////////////////
	//////////////////////////////////////////////
	///// 									 /////
	///// 				EXAMS 				 /////
	///// 									 /////
	//////////////////////////////////////////////
	//////////////////////////////////////////////

	////////////////
	// SHOW EXAMS //
	////////////////

	@GetMapping("/subject/{subjectId}/type-exams")
	public ModelAndView showExam(@PathVariable Long subjectId) {

		ModelAndView modelAndView = new ModelAndView();

		Optional<Subject> s = subjectRepository.findById(subjectId);

		if (s.isPresent()) {

			ArrayList<Exam> exams = examRepository.findAllBySubjectId(subjectId);

			modelAndView.addObject("name", s.get().getName());
			modelAndView.addObject("exams", exams);
			modelAndView.setViewName("type_exams");

		} else {
			modelAndView.addObject("error", "Asignatura no encontrada");
			modelAndView.setViewName("error_view");
		}


		return modelAndView;
	}


	//////////////////////////////
	// DOWNLOAD AND DELETE EXAM //
	//////////////////////////////

	@GetMapping("/subject/{subjectId}/type-exams/download")
	public ResponseEntity<byte[]> downloadFile(@PathVariable Long subjectId, @RequestParam Long examId) {

		Exam exam = examService.getFile(examId);

    	return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + exam.getName() + "." + exam.getType() +"\"")
        .body(exam.getData());
  	}


	@SuppressWarnings("null")
	@GetMapping("/subject/{subjectId}/type-exams/delete")
	public void deleteFile(@PathVariable Long subjectId, @RequestParam Long examId, HttpServletResponse response) throws IOException {

		Optional<Subject> s = subjectRepository.findById(subjectId);

		if (s.isPresent()) {
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("------------------------------------------------------------------------------------");
			examRepository.deleteById(examId);
			
			response.sendRedirect("/redirection/" +subjectId);
		
		}

	}


	//////////////
	// ADD EXAM //
	//////////////
	
	@GetMapping("/subject/{subjectId}/add-exam")
	public ModelAndView showCreateExam(@PathVariable Long subjectId) {

		Optional<Subject> s = subjectRepository.findById(subjectId);
		ModelAndView modelAndView = new ModelAndView();

		if(s.isPresent()){
			modelAndView.setViewName("add_new_exam");
			modelAndView.addObject("name", s.get().getName());
			modelAndView.addObject("error", "");

		} else {
			modelAndView.addObject("error", "Asignatura no encontrada");
			modelAndView.setViewName("error_view");
		}

		return modelAndView;
	}



	@PostMapping("/subject/{subjectId}/add-exam")
	public void showCreateExam(@PathVariable Long subjectId, HttpServletRequest request, HttpServletResponse response,
			Model model,
			@RequestParam String nameExam,
			@RequestParam(required = false) MultipartFile file) throws IOException {


		Optional<Subject> s = subjectRepository.findById(subjectId);

		if (s.isPresent()) { // if subject is in db

			if (nameExam != null) { // if the information is complete

				examService.store(file, s.get(), nameExam);
				response.sendRedirect("/teachers/subject/"+subjectId+"/type-exams");
			}
		}else{
			response.sendRedirect("/error");
		}
	}


 

	//////////////////
	// CORRECT EXAM //
	//////////////////

	//	SHOW THE STUDENT EXAMS //
	@SuppressWarnings("null")
	@GetMapping("/subject/{subjectId}/type-exams/{examId}/correct-exams")
	public ModelAndView showStudentExam(@PathVariable Long subjectId, @PathVariable Long examId) {

		ModelAndView modelAndView = new ModelAndView();

		Optional<Subject> s = subjectRepository.findById(subjectId);

		if (s.isPresent()) {

			Optional<Exam> e = examRepository.findById(examId);

			if (e.isPresent()) {
				ArrayList<ExamStudent> examsStudent = examStudentRepository.findByExamId(examId);

				modelAndView.addObject("name", s.get().getName());
				modelAndView.addObject("examsStudent", examsStudent);
				modelAndView.addObject("examsStudentName", e.get().getName());
				modelAndView.setViewName("correct_exams");
			} else {
				modelAndView.addObject("error", "Exam not found");
				modelAndView.setViewName("error_view");
			}
			

		} else {
			modelAndView.addObject("error", "Subject not found");
			modelAndView.setViewName("error_view");
		}

		return modelAndView;
  	}


	// SAVE NOTE EXAM //
	@SuppressWarnings("null")
	@PostMapping("/subject/{subjectId}/type-exams/{examId}/correct-exams")
	public void showStudentExam(@PathVariable Long subjectId, @PathVariable Long examId, HttpServletRequest request, HttpServletResponse response,
			Model model,
			@RequestParam(required = false) String mark,
			@RequestParam(required = false) String examStudentId) throws IOException {

	
		Optional<Subject> s = subjectRepository.findById(subjectId);
		Long examStudentIdLong = Long.parseLong(examStudentId);
		Double markDouble = Double.parseDouble(mark);

		if (s.isPresent()) { 
			Optional<ExamStudent> examStudent = examStudentRepository.findById(examStudentIdLong);

			if(examStudent.isPresent()){
				examStudent.get().setMark(markDouble);
				examStudentRepository.save(examStudent.get());
			}
		}else{
			response.sendRedirect("/error");
		}

		response.sendRedirect("/teachers/subject/"+subjectId+"/type-exams/"+examId+"/correct-exams");
	}


	// DOWNLOAD PDF STUDENT EXAMS //
	@GetMapping("/subject/{subjectId}/type-exams/{examId}/download")
	public ResponseEntity<byte[]> downloadFileStudent(@PathVariable Long subjectId, @RequestParam Long examStudentId) {

		ExamStudent examStudent = examStudentService.getFile(examStudentId);

    	return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + examStudent.getNameStudent() + "\"")
        .body(examStudent.getData());
  	}



	//////////////////////////////////////////////
	//////////////////////////////////////////////
	///// 									 /////
	///// 			SHOW EXAMS 				 /////
	///// 									 /////
	//////////////////////////////////////////////
	//////////////////////////////////////////////

	////////////////
	// SHOW MARKS //
	////////////////

	
	@GetMapping("/subject/{subjectId}/marks")
	public ModelAndView showMarks(@PathVariable Long subjectId){

		ModelAndView modelAndView = new ModelAndView();

		Optional<Subject> s = subjectRepository.findById(subjectId);

		if (s.isPresent()){


			Map<String, ArrayList<ExamStudent>> table = new HashMap<>();
			Set<Map.Entry<String, ArrayList<ExamStudent>>> entrySet = table.entrySet();

			
			ArrayList<ExamStudent> listExamsStudent = new ArrayList<>();
			ArrayList<Exam> listExams = examRepository.findAllBySubjectId(subjectId);
			ArrayList<String> nums = new ArrayList<>();	
			int n = 0;

			for(Exam exam: listExams){
				listExamsStudent = examStudentRepository.findAllByExamId(exam.getId());

				table.put(exam.getName(), listExamsStudent);
				nums.add(String.valueOf(n));
				n++;
			}

			System.out.println("--------------------------------------------------");
			System.out.println(" ");
			System.out.println(table);
			System.out.println(" ");
			System.out.println("--------------------------------------------------");

			modelAndView.addObject("table", entrySet);
			modelAndView.addObject("num", 1);
			modelAndView.addObject("name", s.get().getName());
			modelAndView.addObject("listExams", listExams);
			modelAndView.setViewName("marks");


		}else{
			modelAndView.addObject("error", "Subject not found");
			modelAndView.setViewName("error_view");
		}

		return modelAndView;
	}





	//////////////////////////////////////////////
	//////////////////////////////////////////////
	///// 									 /////
	///// 				Forum	 			 /////
	///// 									 /////
	//////////////////////////////////////////////
	//////////////////////////////////////////////

	////////////////
	// SHOW FORUM //
	////////////////

	// SHOW FORUM //
	@GetMapping("/subject/{subjectId}/forum")
	public ModelAndView showForum(@PathVariable Long subjectId) {

		ModelAndView modelAndView = new ModelAndView();
		Optional<Subject> subject = subjectRepository.findById(subjectId);
		ArrayList<Forum> forumList = forumRepository.findBySubjectId(subjectId);

		if (subject.isPresent()) {

			modelAndView.addObject("forumList", forumList);
			modelAndView.addObject("name", subject.get().getName());
			modelAndView.setViewName("forum");

		} else {
			modelAndView.addObject("error", "Subject not found");
			modelAndView.setViewName("error_view");
		}

		return modelAndView;
  	}

	// SAVE COMMENT //
	@PostMapping("/subject/{subjectId}/forum")
	public void showForum(@PathVariable Long subjectId, /*@PathVariable name,*/ HttpServletRequest request, HttpServletResponse response,
			Model model,
			@RequestParam String comment) throws IOException {

		Optional<Subject> s = subjectRepository.findById(subjectId);

		if (s.isPresent()) {

			Forum forum = new Forum("Insert URL name", comment);
			forum.setSubject(s.get());

			forumRepository.save(forum);
			response.sendRedirect("/teachers/subject/"+subjectId+"/forum");

		}else{
			response.sendRedirect("/error");
		}

	}

	// DELETE COMMENT //
	@SuppressWarnings("null")
	@GetMapping("/subject/{subjectId}/forum/delete")
	public void deleteForum(@PathVariable Long subjectId, @RequestParam Long forumId, HttpServletResponse response) throws IOException{

		Optional<Subject> s = subjectRepository.findById(subjectId);

		if (s.isPresent()) {

			forumRepository.deleteById(forumId);
			response.sendRedirect("/teachers/subject/"+subjectId+"/forum");

		}else{
			response.sendRedirect("/error");
		}

	}


	
	@GetMapping("/")
	public Collection<Teacher> getItems() {
		return teacherRepository.findAll();
	}

	@GetMapping("/{id}")
	public Teacher getItem(@PathVariable long id) {

		return teacherRepository.findById(id).orElseThrow();
	}

	@SuppressWarnings("null")
	@PostMapping("/")
	public ResponseEntity<Teacher> createItem(@RequestBody Teacher teacher) {

		teacherRepository.save(teacher);

		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(teacher.getId()).toUri();

		return ResponseEntity.created(location).body(teacher);
	}

	@PutMapping("/{id}")
	public Teacher replaceItem(@PathVariable long id, @RequestBody Teacher newteacherRepository) {

		teacherRepository.findById(id).orElseThrow();

		newteacherRepository.setId(id);
		teacherRepository.save(newteacherRepository);
			
		return newteacherRepository;
	}

	@DeleteMapping("/{id}")
	public Teacher deleteItem(@PathVariable long id) {

		Teacher item = teacherRepository.findById(id).orElseThrow();

		teacherRepository.deleteById(id);
		
		return item;
	}
}