package es.codeurjc.helloworldvscode.Entitys;

import java.sql.Blob;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

@Entity
public class Admin extends User {

    public Admin(){}

    public Admin(String firstName, String lastName, String email, String password, byte[] profile) {
        super(firstName, lastName, email, password, profile, "ADMIN");

    }

    public Admin(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, "ADMIN");

    }

    public Admin(User t) {
        super(t);
        
    }
    
}
