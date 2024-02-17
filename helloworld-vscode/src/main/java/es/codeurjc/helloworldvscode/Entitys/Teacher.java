package es.codeurjc.helloworldvscode.Entitys;

import java.util.HashSet;
import java.util.Set;
import java.sql.Blob;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;

@Entity(name = "teacher")
public class Teacher {

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

    @ManyToMany(mappedBy = "students")
    private Set<Subject> subjects;

    // Constructors
    public Teacher(){}

    public Teacher(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.profilePicture = null;
    }

    public Teacher(String firstName, String lastName, String email, String password, Blob profile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        setPassword(password);
        this.profilePicture = profile;
    }

    public Teacher(Teacher t) {
        this.firstName = t.firstName;
        this.lastName = t.lastName;
        this.email = t.email;
        this.password = "";
        this.profilePicture = t.profilePicture;
        this.subjects = new HashSet<>();
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

    public Set<Subject> getSubjects() {
        return subjects;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

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

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    // Add any additional methods needed for your logic
}
