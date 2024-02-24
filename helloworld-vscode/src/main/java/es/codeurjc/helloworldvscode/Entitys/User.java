package es.codeurjc.helloworldvscode.Entitys;

import java.sql.Blob;
import java.util.ArrayList;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import es.codeurjc.helloworldvscode.enumerate.Role;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
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
    private final Role role;

    public User(){
        this.role = null;}

    public User(String firstName, String lastName, String email, String password, Blob profile, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        setPassword(password);
        this.profilePicture = profile;
        this.role = role;
    }

    public User(String firstName, String lastName, String email, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        setPassword(password);
        this.profilePicture = null;
        this.role = role;
    }

    public User(User t) {
        this.firstName = t.firstName;
        this.lastName = t.lastName;
        this.email = t.email;
        this.password = "";
        this.profilePicture = t.profilePicture;
        this.role = t.role;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public Role getRole() {
        return role;
    }
    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }
    public Blob getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(Blob profilePicture) {
        this.profilePicture = profilePicture;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
}
