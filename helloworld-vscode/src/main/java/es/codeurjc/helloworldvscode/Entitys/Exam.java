package es.codeurjc.helloworldvscode.Entitys;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @NotNull(message = "Exam statement is required")
    private byte[] statement; // Assuming this is a PDF file.

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamStudent> examStudents;

    // Constructors
    public Exam() {
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
