package es.codeurjc.helloworldvscode.Entitys;

import jakarta.persistence.*;

@Entity
public class ExamStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private byte[] response; // Assuming this is a PDF file containing the student's response.

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

    // Include a constructor with parameters if needed

    // Getters
    public Long getId() {
        return id;
    }

    public byte[] getResponse() {
        return response;
    }

    public Exam getExam() {
        return exam;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setResponse(byte[] response) {
        this.response = response;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    // Add any additional methods needed for your logic
}
