package es.codeurjc.helloworldvscode.Entitys;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private byte[] profilePicture;

    @ManyToMany(mappedBy = "students")
    private Set<Subject> subjects;

    // Constructors
    public Teacher(String firstName, String lastName, String email, String password) {

        super();

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

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

    public byte[] getProfilePicture() {
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
        this.password = password;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    // Add any additional methods needed for your logic
}
