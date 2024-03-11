package es.codeurjc.helloworldvscode.Entitys;

import jakarta.persistence.*;

@Entity
public class ExamStudent {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(length = 1000000)
    private byte[] data; 

    private double mark;

    private String type;


    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;


    // Constructors
    public ExamStudent() {
    }

    public ExamStudent (byte[] data, Exam exam, Student student ){
    this.data = data;
    this.exam=exam;
    this.student=student;

    }

    public ExamStudent(double mark, String type, byte[] data, Exam exam, Student student) {
        this.mark = mark;
        this.type = type;
        this.data = data;
        this.exam = exam;
        this.student = student;
    }

    // Getters
    public String getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public double getMark() {
        return mark;
    }

    public byte[] getData() {
        return data;
    }

    public Exam getExam() {
        return exam;
    }

    // Setters
    public void setMark(double mark) {
        this.mark = mark;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNameStudent(){
        return student.getFirstName()+student.getLastName();
    }
}