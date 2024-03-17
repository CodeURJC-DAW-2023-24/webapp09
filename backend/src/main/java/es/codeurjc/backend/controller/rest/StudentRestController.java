package es.codeurjc.backend.controller.rest;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.backend.model.Exam;
import es.codeurjc.backend.model.Forum;
import es.codeurjc.backend.model.Student;
import es.codeurjc.backend.model.Subject;
import es.codeurjc.backend.services.ExamService;
import es.codeurjc.backend.services.ExamStudentService;
import es.codeurjc.backend.services.ForumService;
import es.codeurjc.backend.services.StudentService;
import es.codeurjc.backend.services.SubjectService;

@RestController
@RequestMapping("/api/students")
public class StudentRestController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ExamService examService;

    @Autowired
    private ForumService forumService;

    @Autowired
    private ExamStudentService examStudentService;

    @GetMapping("/subjects/{subjectId}")
    public ResponseEntity<?> showSubjectDetails(HttpServletRequest request, @PathVariable Long subjectId) {
        Principal principal = request.getUserPrincipal();
        boolean isStudent = request.isUserInRole("STUDENT");
        
        if (isStudent && principal != null) {
            Student student = studentService.getStudentByEmail(principal.getName());
            Subject subject = subjectService.getSubjectById(subjectId);
            List<Exam> exams = examStudentService.getIncompleteExamsStudent(student, subject);
            List<Forum> forums = subject.getForums();

            if (student != null && subject != null) {
                return ResponseEntity.ok().body(subject);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(403).body("Forbidden"); 
        }
    }

    @PostMapping("/subjects/{subjectId}/forums")
    public ResponseEntity<String> createForumPost(HttpServletRequest request,
                                                  @PathVariable Long subjectId,
                                                  @RequestParam String comment) {
        Principal principal = request.getUserPrincipal();
        Subject subject = subjectService.getSubjectById(subjectId);

        if (principal != null && subject != null) {
            Forum forum = new Forum(principal.getName(), comment, subject);
            forumService.setForum(forum);
            return ResponseEntity.ok("Forum post created successfully.");
        } else {
            return ResponseEntity.badRequest().body("Error creating forum post.");
        }
    }

    @GetMapping("/subjects/{subjectId}/exams/{examId}/download")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long subjectId, @PathVariable Long examId) {
        Exam exam = examService.getFile(examId);

        if (exam != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + exam.getName() + "." + exam.getType() + "\"")
                    .body(exam.getData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/subjects/{subjectId}/exams/{examId}/upload")
    public ResponseEntity<String> uploadFile(HttpServletRequest request,
                                              @PathVariable Long subjectId,
                                              @PathVariable Long examId,
                                              @RequestParam("file") MultipartFile file) throws IOException {
        Principal principal = request.getUserPrincipal();
        Student student = studentService.getStudentByEmail(principal.getName());
        Exam exam = examService.getById(examId);

        if (student != null && exam != null) {
            examStudentService.store(file, exam, 0, student);
            return ResponseEntity.ok("File uploaded successfully.");
        } else {
            return ResponseEntity.badRequest().body("Error uploading file.");
        }
    }
}
