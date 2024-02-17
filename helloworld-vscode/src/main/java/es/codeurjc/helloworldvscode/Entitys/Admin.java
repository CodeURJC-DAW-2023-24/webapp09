package es.codeurjc.helloworldvscode.Entitys;

import com.fasterxml.jackson.annotation.JsonView;
import es.codeurjc.helloworldvscode.enumerate.Role;
import jakarta.persistence.*;

public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   // @JsonView(View.Admin.class)  --------------------------- PONER CUANDO ESTÉ LA SEGURIDAD (View será una clase)
    private Integer id;

    @Column(nullable = false)
   // @JsonView(View.Admin.class)
    private String firstName;

    @Column(nullable = false)
   // @JsonView(View.Admin.class)
    private String email;

    @Column(nullable = false)
   // @JsonView(View.Admin.class)
    private String password;

    @Enumerated(EnumType.STRING)
   // @JsonView(View.Admin.class)
    private Role role = Role.ROLE_ADMIN;

    public Admin(){}

    public Admin(String firstName, String email, String password){
        this.firstName = firstName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() { return firstName;}

    public void setFirstName(String firstName) { this.firstName = firstName;}

    public String getEmail() { return email;}

    public void setEmail(String email) { this.email = email;}

    public String getPassword() { return password;}

    public void setPassword(String password) { this.password = password;}

    public Role getRole() { return this.role;}
    
}
