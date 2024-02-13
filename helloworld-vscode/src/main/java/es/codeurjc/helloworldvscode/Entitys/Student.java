package es.codeurjc.helloworldvscode.Entitys;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @NotBlank(message = "Name not null")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Surname not null")
    @Size(max = 100)
    private String surname;

    @NotBlank(message = "mail not null")
    @Email(message = "invalid mail")
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    @NotBlank(message = "password not null")
    private String password;

    @Lob
    private byte[] profilePhoto;

    public Student() {
    }

    public Student(String name, String surname, String email, String password, byte[] profilePhoto) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.profilePhoto = profilePhoto;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}

