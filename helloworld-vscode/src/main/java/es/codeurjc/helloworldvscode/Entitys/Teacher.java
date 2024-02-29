package es.codeurjc.helloworldvscode.Entitys;

import java.util.ArrayList;
import java.util.List;
import java.sql.Blob;
import com.fasterxml.jackson.annotation.JsonProperty;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import es.codeurjc.helloworldvscode.enumerate.Role;
import jakarta.persistence.*;

@Entity(name = "teacher")
public class Teacher {

    @Id
    @Column(name = "teacherId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstName;
    
    private String lastName;
   
    private String email;
    
    private String password;

    @Lob
    private Blob profilePicture;
    
    @Enumerated(EnumType.STRING)
    private final Role role = Role.ROLE_TEACHER;

    @ManyToMany(mappedBy = "teachers")
    private List<Subject> subjects;

    // Constructors
    public Teacher(){}

    public Teacher(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.profilePicture = null;
        this.subjects = new ArrayList<>();
    }

    public Teacher(String firstName, String lastName, String email, String password, Blob profile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        setPassword(password);
        this.profilePicture = profile;
        this.subjects = new ArrayList<>();
    }

    public Teacher(Teacher t) {
        this.firstName = t.firstName;
        this.lastName = t.lastName;
        this.email = t.email;
        this.password = "";
        this.profilePicture = t.profilePicture;
        this.subjects = new ArrayList<>();
    }
    // Include a constructor with parameters if needed

    // Getters
    public Long getTeacherId() {
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

    public Role getRole(){ return this.role;}

    // Setters
    public void setTeacherId(Long id) {
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

        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //this.password = passwordEncoder.encode(password);

        this.password = password;
    }

    public void setProfilePicture(Blob profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public void setOneSubject(Subject s){
        this.subjects.add(s);
    }

    //Others 
    @SuppressWarnings("unlikely-arg-type")
    public void deleteSubjectId(Long id){
        
        for (Subject subject : subjects) {
            if (subject.getSubjectId() == id) {
                subjects.remove(subjects);
            }
        }
    }
}
