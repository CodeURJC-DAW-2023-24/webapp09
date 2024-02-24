package es.codeurjc.helloworldvscode.Entitys;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.sql.Blob;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import es.codeurjc.helloworldvscode.enumerate.Role;
import jakarta.persistence.*;

@Entity(name = "teacher")
public class Teacher extends User{

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;
    // @Column(nullable = false)
    // private String firstName;
    // @Column(nullable = false)
    // private String lastName;
    // @Column(nullable = false)
    // private String email;
    // @Column(nullable = false)
    // private String password;
    // @Column
    // @Lob
    // private Blob profilePicture;
    // @Enumerated(EnumType.STRING)
    // private final Role role = Role.ROLE_TEACHER;


    @ManyToMany(mappedBy = "students")
    private List<Subject> subjects;



    // Constructors
    public Teacher(){}

    public Teacher(String firstName, String lastName, String email, String password, Blob profile) {
        super(firstName, lastName, email, password, profile, Role.ROLE_TEACHER);

    }

    public Teacher(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, Role.ROLE_TEACHER);

    }

    public Teacher(User t) {
        super(t);
        this.subjects = new ArrayList<>();
    }

    // Include a constructor with parameters if needed

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }


    // Add any additional methods needed for your logic
}
