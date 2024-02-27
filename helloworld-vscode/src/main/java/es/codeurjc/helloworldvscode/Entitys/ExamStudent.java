package es.codeurjc.helloworldvscode.Entitys;

import jakarta.persistence.*;

@Entity
public class ExamStudent {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="examStudentId")
    private Long id;

    private byte[] data; // Assuming this is a PDF file containing the student's response.

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    private double mark;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;



    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;



    // Additional fields can include student information if needed, depending on the model.

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

    // Include a constructor with parameters if needed

    // Getters
    public Long getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }

    public Exam getExam() {
        return exam;
    }

    // Setters
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

    // Add any additional methods needed for your logic
}
