package es.codeurjc.helloworldvscode.Entitys;

import es.codeurjc.helloworldvscode.enumerate.Role;
import jakarta.persistence.*;

@Entity(name = "admin")
public class Admin extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   // @JsonView(View.Admin.class)  --------------------------- PONER CUANDO ESTÉ LA SEGURIDAD (View será una clase)
    private Long id;

    @Column(nullable = false)
   // @JsonView(View.Admin.class)
    private String firstName;

    @Column(nullable = false)
   // @JsonView(View.Admin.class)
    private String lastName;

    @Column(nullable = false)
   // @JsonView(View.Admin.class)
    private String email;

    @Column(nullable = false)
   // @JsonView(View.Admin.class)
    private String password;

    @Enumerated(EnumType.STRING)
   // @JsonView(View.Admin.class)
    private static Role role = Role.ROLE_ADMIN;


    public Admin(){}

    public Admin(String firstName, String lastName,String email, String password){
        super( firstName, lastName, email, password, role);
    }

    public void setId(Long id) { this.id = id;}
    public Long getId() { return id;}

    public String getFirstName() { return firstName;}

    public void setFirstName(String firstName) { this.firstName = firstName;}

    public String getEmail() { return email;}

    public void setEmail(String email) { this.email = email;}

    public String getPassword() { return password;}

    public void setPassword(String password) { this.password = password;}

    public Role getRole() { return this.role;}
    
}
