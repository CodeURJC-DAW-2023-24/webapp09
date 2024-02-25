package es.codeurjc.helloworldvscode.Entitys;

import jakarta.persistence.*;

@Entity
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "subject_id")//, nullable = false)
    private Subject subject;

    // Constructors
    public Forum() {
    }

    // Include a constructor with parameters if needed

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }

    public Subject getSubject() {
        return subject;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    // Add any additional methods needed for your logic
}
