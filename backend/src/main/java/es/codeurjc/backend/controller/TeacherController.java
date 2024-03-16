package es.codeurjc.backend.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import es.codeurjc.backend.model.Exam;
import es.codeurjc.backend.model.ExamStudent;
import es.codeurjc.backend.model.Forum;
import es.codeurjc.backend.model.Student;
import es.codeurjc.backend.model.Subject;
import es.codeurjc.backend.model.Teacher;
import es.codeurjc.backend.services.ExamService;
import es.codeurjc.backend.services.ExamStudentService;
import es.codeurjc.backend.services.ForumService;
import es.codeurjc.backend.services.StudentService;
import es.codeurjc.backend.services.SubjectService;
import es.codeurjc.backend.services.TeacherService;

@RestController
@RequestMapping("/teachers/")
public class TeacherController {

	@Autowired
	private TeacherService teacherService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private ForumService forumService;
	@Autowired
	private SubjectService subjectService;

	@Autowired
	private ExamService examService;
	@Autowired
	private ExamStudentService examStudentService;

	////////////////////////
	// UPDATE DESCRIPTION //
	////////////////////////

	@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			model.addAttribute("logged", true);
		} else {
			model.addAttribute("logged", false);
		}
	}

	@GetMapping("/subject/{subjectId}/general-information")
	public ModelAndView subjectOneSubjAdmin(@PathVariable Long subjectId) {

		ModelAndView modelAndView = new ModelAndView();
		Subject subject = subjectService.getById(subjectId);

		String description = subject.getDescription();
		String allInfo = subject.getAllInfo();
		String name = subject.getName();

		List<Student> students = studentService.getAllBySubjectsId(subjectId);

		modelAndView.setViewName("general_information");
		modelAndView.addObject("students", students);
		modelAndView.addObject("description", description);
		modelAndView.addObject("allInfo", allInfo);
		modelAndView.addObject("name", name);

		// obtain data for graphic
		List<ExamStudent> exams = examStudentService.getAll();
		int[] conteoNotas = new int[5];
		for (ExamStudent exam : exams) {
			double nota = exam.getMark();
			if (nota >= 0 && nota < 2) {
				conteoNotas[0]++;
			} else if (nota >= 2 && nota < 4) {
				conteoNotas[1]++;
			} else if (nota >= 4 && nota < 6) {
				conteoNotas[2]++;
			} else if (nota >= 6 && nota < 8) {
				conteoNotas[3]++;
			} else if (nota >= 8 && nota <= 10) {
				conteoNotas[4]++;
			}
			System.out.println("NOTA EXAMEN: " + exam.getMark());
		}

		System.out.println("CONTEO DE NOTAS:");
		System.out.println("0-2: " + conteoNotas[0]);
		System.out.println("2-4: " + conteoNotas[1]);
		System.out.println("4-6: " + conteoNotas[2]);
		System.out.println("6-8: " + conteoNotas[3]);
		System.out.println("8-10: " + conteoNotas[4]);

		modelAndView.addObject("conteoNotas", conteoNotas);

		return modelAndView;
	}

	@PostMapping("/subject/{subjectId}/general-information")
	public void subjectOneSubjAdmin(@PathVariable Long subjectId, HttpServletResponse response,
			Model model,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) String allInfo) throws IOException {

		Subject subject = subjectService.getById(subjectId);

		if (!subject.getDescription().equals(description)) {

			if (description != null) {
				subject.setDescription(description);
			}
			if (allInfo != null) {
				subject.setAllInfo(allInfo);
			}

			subjectService.setSubject(subject);
		}

		response.sendRedirect("/admins/subject/" + subjectId + "/general-information");

	}

	/////////////////////
	// DELETE STUDENT //
	/////////////////////

	@GetMapping("/subject/{subjectId}/general-information/delete")
	public void deleteStudent(@PathVariable Long subjectId, @RequestParam(required = false) Long studentId,
			HttpServletResponse response) throws IOException {

		Subject subject = subjectService.getById(subjectId);

		if (studentId != null) {
			Student st = studentService.getStudentById(studentId);
			st.deleteSubjectId(subjectId);
			subject.deleteStudentId(studentId);

			studentService.setStudent(st);
			subjectService.setSubject(subject);
		}

		response.sendRedirect("/teachers/subject/" + subjectId + "/general-information");

	}

	//////////////////
	// ADD STUDENT //
	//////////////////

	@GetMapping("/subject/{subjectId}/add-student")
	public ModelAndView showCreateStudent(@PathVariable Long subjectId, @RequestParam String addorcreate,
			@RequestParam String info) {
		ModelAndView modelAndView = new ModelAndView();

		Subject subject = subjectService.getById(subjectId);

		if (info.equals("true")) {
			modelAndView.addObject("error", "");
		} else if (info.equals("false")) {
			modelAndView.addObject("error", "Student not found");
		} else if (info.equals("repete")) {
			modelAndView.addObject("error", "Student is already saved");
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

		modelAndView.addObject("rol", "teachers");
		modelAndView.addObject("user", "student");
		modelAndView.addObject("name", subject.getName());

		return modelAndView;
	}

	@PostMapping("/subject/{subjectId}/add-student")
	public void showCreateStudent(@RequestParam String addorcreate, @PathVariable Long subjectId,
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
		Student student = new Student(firstName, lastName, email, password);

		// Find subject by name
		Subject subject = subjectService.getById(subjectId);

		// if need to create a new student
		if ("new".equals(addorcreate)) {
			if (firstName != null && password != null && lastName != null) { // if the data is complete

				// the student is not in the list yet
				if (!studentService.isExistsByEmail(student.getEmail())) {
					studentService.setStudent(student);
					subject.setOneStudent(student);
					subjectService.setSubject(subject);
					response.sendRedirect("/teachers/subject/" + subjectId + "/general-information");
				} else {
					response.sendRedirect(
							"/subject/" + subjectId + "/add-student?addorcreate=" + addorcreate + "&info=repete");
				}

			} else { // if the data isn't complete
				response.sendRedirect(
						"/subject/" + subjectId + "/add-student?addorcreate=" + addorcreate + "&info=incomplete");
			}

		} else { // if the student is created
			Student existingEstudent = studentService.getStudentByEmail(email); // find student for updated

			subject.setOneStudent(existingEstudent);
			subjectService.setSubject(subject);
			response.sendRedirect("/teachers/subject/" + subjectId + "/general-information");

		}

	}

	//////////////////////////////////////////////
	//////////////////////////////////////////////
	///// /////
	///// EXAMS /////
	///// /////
	//////////////////////////////////////////////
	//////////////////////////////////////////////

	////////////////
	// SHOW EXAMS //
	////////////////

	@GetMapping("/subject/{subjectId}/type-exams")
	public ModelAndView showExam(@PathVariable Long subjectId) {

		ModelAndView modelAndView = new ModelAndView();

		Subject subject = subjectService.getById(subjectId);

		Page<Exam> moreExams = examService.getAllBySubjectIdPage(subjectId, PageRequest.of(0, 3));

		modelAndView.addObject("name", subject.getName());
		modelAndView.addObject("moreExams", moreExams);
		modelAndView.setViewName("type_exams");

		return modelAndView;
	}

	//////////////////////////////
	// DOWNLOAD AND DELETE EXAM //
	//////////////////////////////

	@GetMapping("/subject/{subjectId}/type-exams/download")
	public ResponseEntity<byte[]> downloadFile(@PathVariable Long subjectId, @RequestParam Long examId) {

		Exam exam = examService.getFile(examId);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + exam.getName() + "." + exam.getType() + "\"")
				.body(exam.getData());
	}

	@GetMapping("/subject/{subjectId}/type-exams/delete")
	public void deleteFile(@PathVariable Long subjectId, @RequestParam Long examId, HttpServletResponse response)
			throws IOException {

		examService.deleteById(examId);

		response.sendRedirect("/redirection/" + subjectId);

	}

	//////////////
	// ADD EXAM //
	//////////////

	@GetMapping("/subject/{subjectId}/add-exam")
	public ModelAndView showCreateExam(@PathVariable Long subjectId) {

		Subject subject = subjectService.getById(subjectId);
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("add_new_exam");
		modelAndView.addObject("name", subject.getName());
		modelAndView.addObject("error", "");

		return modelAndView;
	}

	@PostMapping("/subject/{subjectId}/add-exam")
	public void showCreateExam(@PathVariable Long subjectId, HttpServletRequest request, HttpServletResponse response,
			Model model,
			@RequestParam String nameExam,
			@RequestParam(required = false) MultipartFile file) throws IOException {

		Subject subject = subjectService.getById(subjectId);

		if (nameExam != null) { // if the information is complete

			examService.store(file, subject, nameExam);
			response.sendRedirect("/teachers/subject/" + subjectId + "/type-exams");
		}

	}

	//////////////////
	// CORRECT EXAM //
	//////////////////

	// SHOW THE STUDENT EXAMS //
	@GetMapping("/subject/{subjectId}/type-exams/{examId}/correct-exams")
	public ModelAndView showStudentExam(@PathVariable Long subjectId, @PathVariable Long examId) {
		System.out.println("------------------------------------------------");
		System.out.println("------------------------------------------------");

		ModelAndView modelAndView = new ModelAndView();

		Subject subject = subjectService.getSubjectById(subjectId);

		List<ExamStudent> examsStudent = examStudentService.getByExamId(examId);

		modelAndView.addObject("name", subject.getName());
		modelAndView.addObject("examsStudent", examsStudent);
		modelAndView.addObject("examsStudentName", subject.getName());
		modelAndView.setViewName("correct_exams");

		return modelAndView;
	}

	// SAVE MARK EXAM //
	@PostMapping("/subject/{subjectId}/type-exams/{examId}/correct-exams")
	public void showStudentExam(@PathVariable Long subjectId, @PathVariable Long examId, HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@RequestParam(required = false) String mark,
			@RequestParam(required = false) String examStudentId) throws IOException {

		Long examStudentIdLong = Long.parseLong(examStudentId);
		Double markDouble = Double.parseDouble(mark);

		ExamStudent examStudent = examStudentService.getById(examStudentIdLong);

		examStudent.setMark(markDouble);
		examStudentService.setExamStudent(examStudent);

		response.sendRedirect("/teachers/subject/" + subjectId + "/type-exams/" + examId + "/correct-exams");
	}

	// DOWNLOAD PDF STUDENT EXAMS //
	@GetMapping("/subject/{subjectId}/type-exams/{examId}/download")
	public ResponseEntity<byte[]> downloadFileStudent(@PathVariable Long subjectId, @RequestParam Long examStudentId) {

		ExamStudent examStudent = examStudentService.getFile(examStudentId);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + examStudent.getNameStudent() + "." + examStudent.getType() + "\"")
				.body(examStudent.getData());
	}

	//////////////////////////////////////////////
	//////////////////////////////////////////////
	///// /////
	///// SHOW MARKS /////
	///// /////
	//////////////////////////////////////////////
	//////////////////////////////////////////////

	////////////////
	// SHOW MARKS //
	////////////////

	@GetMapping("/subject/{subjectId}/marks")
	public ModelAndView showMarks(@PathVariable Long subjectId) {

		ModelAndView modelAndView = new ModelAndView();

		Subject subject = subjectService.getSubjectById(subjectId);

		Map<String, ArrayList<ExamStudent>> table = new HashMap<>();
		Set<Map.Entry<String, ArrayList<ExamStudent>>> entrySet = table.entrySet();

		List<ExamStudent> listExamsStudent = new ArrayList<>();
		List<Exam> listExams = examService.getAllBySubjectId(subjectId);
		ArrayList<String> nums = new ArrayList<>();
		int n = 0;

		for (Exam exam : listExams) {
			listExamsStudent = examStudentService.getByExamId(exam.getId());

			table.put(exam.getName(), (ArrayList<ExamStudent>) listExamsStudent);
			nums.add(String.valueOf(n));
			n++;
		}

		modelAndView.addObject("table", entrySet);
		modelAndView.addObject("num", 1);
		modelAndView.addObject("name", subject.getName());
		modelAndView.addObject("listExams", listExams);
		modelAndView.setViewName("marks");

		return modelAndView;
	}

	//////////////////////////////////////////////
	//////////////////////////////////////////////
	///// /////
	///// Forum /////
	///// /////
	//////////////////////////////////////////////
	//////////////////////////////////////////////

	////////////////
	// SHOW FORUM //
	////////////////

	// SHOW FORUM //
	@GetMapping("/subject/{subjectId}/forum")
	public ModelAndView showForum(@PathVariable Long subjectId) {

		ModelAndView modelAndView = new ModelAndView();
		Subject subject = subjectService.getSubjectById(subjectId);
		List<Forum> forumList = forumService.getBySubjectId(subjectId);

		modelAndView.addObject("forumList", forumList);
		modelAndView.addObject("name", subject.getName());
		modelAndView.setViewName("forum");

		return modelAndView;
	}

	// SAVE COMMENT //
	@PostMapping("/subject/{subjectId}/forum")
	public void showForum(@PathVariable Long subjectId, /* @PathVariable name, */ HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@RequestParam String comment) throws IOException {

		Subject subject = subjectService.getSubjectById(subjectId);

		Principal principal = request.getUserPrincipal();
		Teacher teacher = teacherService.getByEmail(principal.getName());
		String name = teacher.getFirstName() + " " + teacher.getLastName();

		Forum forum = new Forum(name, comment);
		forum.setSubject(subject);

		forumService.setForum(forum);
		response.sendRedirect("/teachers/subject/" + subjectId + "/forum");

	}

	// DELETE COMMENT //
	@GetMapping("/subject/{subjectId}/forum/delete")
	public void deleteForum(@PathVariable Long subjectId, @RequestParam Long forumId, HttpServletResponse response)
			throws IOException {

		forumService.deleteById(forumId);
		response.sendRedirect("/teachers/subject/" + subjectId + "/forum");
	}
}