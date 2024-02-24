package es.codeurjc.helloworldvscode.Entitys;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private byte[] statement; // Assuming this is a PDF file.

    @ManyToOne
    private Subject subject;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamStudent> examStudents;

    // Constructors
    public Exam() {
    }

    public Exam(byte[] statement, Subject subject) {
        this.statement = statement;
        this.subject = subject;
        this.examStudents = new ArrayList<>();
    }
    // Include a constructor with parameters if needed

    // Getters
    public Long getId() {
        return id;
    }

    public byte[] getStatement() {
        return statement;
    }

    public Subject getSubject() {
        return subject;
    }

    public List<ExamStudent> getExamStudents() {
        return examStudents;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setStatement(byte[] statement) {
        this.statement = statement;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setExamStudents(List<ExamStudent> examStudents) {
        this.examStudents = examStudents;
    }

    // Add any additional methods needed for your logic
}
