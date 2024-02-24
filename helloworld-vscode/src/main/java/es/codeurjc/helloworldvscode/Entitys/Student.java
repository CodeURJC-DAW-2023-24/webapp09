package es.codeurjc.helloworldvscode.Entitys;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import es.codeurjc.helloworldvscode.enumerate.Role;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column
    @Lob
    private Blob profilePicture;

    @Enumerated(EnumType.STRING)
    private final Role role = Role.ROLE_STUDENT;

    @ManyToMany(mappedBy = "students")
    @JsonManagedReference
    private List<Subject> subjects;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamStudent> examStudents;


    // Constructors
    public Student() {
    }


    public Student(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        setPassword(password);
        this.subjects = new ArrayList<>();

    }


    // Include a constructor with parameters if needed

    // Getters
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    // Use JsonProperty on getter to allow password field to be deserialized during JSON parsing
    @JsonProperty
    public String getPassword() {
        return password;
    }

    public Blob getProfilePicture() {
        return profilePicture;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public List<ExamStudent> getExamStudents() {
        return examStudents;
    }

    public Role getRole(){ return this.role;}

    // Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public void setProfilePicture(Blob profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void setExamStudents(List<ExamStudent> examStudents) {
        this.examStudents = examStudents;
    }

    // Add any additional methods needed for your logic
}
