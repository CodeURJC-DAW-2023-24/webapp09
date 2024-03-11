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
    private Long examId;

    @Lob
    @Column (length=1000000)
    private byte[] data; 

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    
    private String name;

    private String type;

    @OneToMany(mappedBy = "exam")
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

    // Getters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }


    public Long getId() {
        return examId;
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
    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long examId) {
        this.examId = examId;
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

    
}