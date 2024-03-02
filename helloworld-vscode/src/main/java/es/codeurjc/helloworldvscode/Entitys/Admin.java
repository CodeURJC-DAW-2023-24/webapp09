package es.codeurjc.helloworldvscode.Entitys;

import java.sql.Blob;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonView;
import es.codeurjc.helloworldvscode.enumerate.Role;
import jakarta.persistence.*;

@Entity
public class Admin extends User {

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
