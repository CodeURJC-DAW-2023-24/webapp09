package es.codeurjc.helloworldvscode.Entitys;

import java.sql.Blob;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonView;
import es.codeurjc.helloworldvscode.enumerate.Role;
import jakarta.persistence.*;

@Entity
public class Admin extends User {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//    // @JsonView(View.Admin.class)  --------------------------- PONER CUANDO ESTÉ LA SEGURIDAD (View será una clase)
//     private Integer id;

//     @Column(nullable = false)
//    // @JsonView(View.Admin.class)
//     private String firstName;

//     @Column(nullable = false)
//    // @JsonView(View.Admin.class)
//     private String email;

//     @Column(nullable = false)
//    // @JsonView(View.Admin.class)
//     private String password;

//     @Enumerated(EnumType.STRING)
//    // @JsonView(View.Admin.class)
//     private Role role = Role.ROLE_ADMIN;

    public Admin(){}

    public Admin(String firstName, String lastName, String email, String password, Blob profile) {
        super(firstName, lastName, email, password, profile, Role.ROLE_ADMIN);

    }

    public Admin(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, Role.ROLE_ADMIN);

    }

    public Admin(User t) {
        super(t);
        
    }
    
}
