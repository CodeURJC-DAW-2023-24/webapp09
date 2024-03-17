package es.codeurjc.backend.controller.rest;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import es.codeurjc.backend.model.Subject;
import es.codeurjc.backend.model.Teacher;
import es.codeurjc.backend.services.SubjectService;
import es.codeurjc.backend.services.TeacherService;

@RestController
@RequestMapping("/api/admins")
public class AdminRestController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/subjects/{subjectId}")
    public ResponseEntity<?> showSubjects(@PathVariable Long subjectId) {
        Subject subject = subjectService.getSubjectById(subjectId);
        if (subject != null) {
            List<Teacher> teachers = teacherService.getAllBySubjectsId(subjectId);
            return ResponseEntity.ok().body(subject);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/subjects/{subjectId}")
    public ResponseEntity<String> updateSubject(@PathVariable Long subjectId,
                                                @RequestParam(required = false) String description,
                                                @RequestParam(required = false) String allInfo) {
        Subject subject = subjectService.getSubjectById(subjectId);
        if (subject != null) {
            if (description != null) {
                subject.setDescription(description);
            }
            if (allInfo != null) {
                subject.setAllInfo(allInfo);
            }
            subjectService.setSubject(subject);
            return ResponseEntity.ok("Subject updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/subjects/{subjectId}")
    public ResponseEntity<String> deleteSubject(@PathVariable Long subjectId) {
        Subject subject = subjectService.getSubjectById(subjectId);
        if (subject != null) {
            subjectService.deleteSubjectId(subjectId);
            return ResponseEntity.ok("Subject deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/subjects/{subjectId}/teachers/{teacherId}")
    public ResponseEntity<String> deleteTeacherFromSubject(@PathVariable Long subjectId, @PathVariable Long teacherId) {
        Subject subject = subjectService.getById(subjectId);
        Teacher teacher = teacherService.getById(teacherId);
        if (subject != null && teacher != null) {
            teacher.deleteSubjectId(subjectId);
            subject.deleteTeacherId(teacherId);
            teacherService.setTeacher(teacher);
            subjectService.setSubject(subject);
            return ResponseEntity.ok("Teacher deleted from subject successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

