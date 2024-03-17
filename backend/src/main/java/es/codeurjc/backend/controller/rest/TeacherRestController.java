package es.codeurjc.backend.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import es.codeurjc.backend.model.*;
import es.codeurjc.backend.services.*;

@RestController
@RequestMapping("/api/teachers/")
public class TeacherRestController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ExamStudentService examStudentService;

    @GetMapping("/subject/{subjectId}/general-information")
    public Map<String, Object> getSubjectGeneralInformation(@PathVariable Long subjectId) {
        Map<String, Object> response = new HashMap<>();
        Subject subject = subjectService.getById(subjectId);
        response.put("subject", subject);
        List<Student> students = studentService.getAllBySubjectsId(subjectId);
        response.put("students", students);
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
        }
        response.put("conteoNotas", conteoNotas);
        return response;
    }

    @PostMapping("/subject/{subjectId}/general-information")
    public void updateSubjectGeneralInformation(@PathVariable Long subjectId, @RequestParam(required = false) String description,
            @RequestParam(required = false) String allInfo) {
        Subject subject = subjectService.getById(subjectId);
        if (description != null) {
            subject.setDescription(description);
        }
        if (allInfo != null) {
            subject.setAllInfo(allInfo);
        }
        subjectService.setSubject(subject);
    }

}

