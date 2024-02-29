package es.codeurjc.helloworldvscode.Entitys;

import com.fasterxml.jackson.annotation.JsonProperty;
import es.codeurjc.helloworldvscode.enumerate.Role;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.*;

@Entity
public class Student extends User {

    @Id
    @Column(name = "studentId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    
    private String lastName;

    private String email;


    private String password;

    @Lob
    private Blob profilePicture;

    @Enumerated(EnumType.STRING)
    private final static Role role = Role.ROLE_STUDENT;

    @ManyToMany(mappedBy = "students")
    private List<Subject> subjects;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamStudent> examStudents;


    // Constructors
    public Student() {
    }

    public Student(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, role);
        this.subjects = new ArrayList<>();
        this.examStudents = new ArrayList<>();

    }


    // Getters
    public Long getStudentId() {
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

    public List<ExamStudent> getExamStudents() {
        return examStudents;
    }

    public Role getRole(){ return this.role;}

   
   
    // Setters
    public void setOneSubjects(Subject s){
        subjects.add(s);
    }

    public void setStudentId(Long id) {
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

    public void setExamStudents(ArrayList<ExamStudent> examStudents) {
        this.examStudents = examStudents;
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
