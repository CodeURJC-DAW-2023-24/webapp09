package es.codeurjc.helloworldvscode.Entitys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="examId")
    private Long id;

    @Lob
    @Column (length=1000000)
    private byte[] data; // Assuming this is a PDF file.

    @ManyToOne
    private Subject subject;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    @OneToMany(mappedBy = "exam")//, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<ExamStudent> examStudents;

    // Constructors
    public Exam() {
    }

    public Exam(byte[] statement, Subject subject) {
        this.data = statement;
        this.subject = subject;
        this.examStudents = new ArrayList<>();
    }

    public Exam(String name, String type, byte[] data, Subject subject) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.subject = subject;
        this.examStudents = new ArrayList<>();
    }
    // Include a constructor with parameters if needed

    // Getters
    public Long getId() {
        return id;
    }

    public byte[] getData() {
        return data;
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

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setExamStudents(List<ExamStudent> examStudents) {
        this.examStudents = examStudents;
    }

    // Add any additional methods needed for your logic
}
