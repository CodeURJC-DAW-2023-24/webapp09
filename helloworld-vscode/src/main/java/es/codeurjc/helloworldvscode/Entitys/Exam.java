package es.codeurjc.helloworldvscode.Entitys;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

@Entity
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject; // Reference to the Subject entity

    @Lob
    private byte[] statement; // PDF statement of the exam

    // No-argument constructor
    public Exam() {
    }

    // Constructor with all fields
    public Exam(Subject subject, byte[] statement) {
        this.subject = subject;
        this.statement = statement;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public byte[] getStatement() {
        return statement;
    }

    public void setStatement(byte[] statement) {
        this.statement = statement;
    }
}
