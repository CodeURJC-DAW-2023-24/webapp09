package es.codeurjc.helloworldvscode.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.helloworldvscode.Entitys.Exam;
import es.codeurjc.helloworldvscode.Entitys.ExamStudent;
import es.codeurjc.helloworldvscode.Entitys.Student;
import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.repository.ExamStudentRepository;
import es.codeurjc.helloworldvscode.repository.StudentRepository;

@Service
public class ExamStudentService {

    @Autowired
    private ExamStudentRepository examStudentRepository;
    @Autowired
    private StudentRepository studentRepository;

    public ExamStudent store(MultipartFile file, Exam e, int note, Student s) throws IOException {

        ExamStudent examStudent = examStudentRepository.findByStudentIdAndExamId(s.getId(), e.getId());
        examStudent.setData(file.getBytes());

        return examStudentRepository.save(examStudent);
    }

    @SuppressWarnings("null")
    public ExamStudent getFile(Long id) {
        return examStudentRepository.findById(id).get();
    }

    public Stream<ExamStudent> getAllFiles() {
        return examStudentRepository.findAll().stream();
    }

    public void createExamsStudent(Subject s, Exam exam) {

        ArrayList<Student> studentsList = studentRepository.findAllBySubjectsId(s.getId());

        ExamStudent examStudent = null;

        for (Student student : studentsList) {

            examStudent = new ExamStudent(0, "pdf", null, exam, student);
            examStudentRepository.save(examStudent);
        }

    }

    // return the incomplete examsStudent
    public List<Exam> getIncompleteExamsStudent(Student student, Subject subject) {

        List<Exam> exams = subject.getExams();
        ArrayList<ExamStudent> examsStudent = examStudentRepository.findAllByStudentId(student.getId());

        for (ExamStudent examStudent : examsStudent) {

            if (exams.contains(examStudent.getExam())) {

                if (examStudent.getData() != null) {
                    exams.remove(examStudent.getExam());
                }
            }
        }

        return exams;

    }

}