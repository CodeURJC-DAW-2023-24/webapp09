package es.codeurjc.helloworldvscode.Entitys;

import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long forumId;

    private String username;

    @Lob
    @Column(length = 1000000)
    private String comment;
    
    @Lob
    @Column(length = 1000000)
    private String date;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    // Constructors
    public Forum() {
    }

    public Forum(String username, String comment){

        this.username = username;
        this.comment = comment;
        
        Date currentDate = new Date(); 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        String currentDateTime = dateFormat. format(currentDate);
        this.date = currentDateTime;

    }

    // Getters
    public Long getForumd() {
        return forumId;
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

    public String getDate() {
        return date;
    }


    // Setters
    public void setForumId(Long forumId) {
        this.forumId = forumId;
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

    public void setDate(String date) {
        this.date = date;
    }
}