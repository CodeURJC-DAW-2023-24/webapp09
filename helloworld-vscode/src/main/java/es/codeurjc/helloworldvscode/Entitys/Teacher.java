package es.codeurjc.helloworldvscode.Entitys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import es.codeurjc.helloworldvscode.enumerate.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "teacher")
public class Teacher extends User{

    @ManyToMany(mappedBy = "students")
    @JsonBackReference
    private List<Subject> subjects;

    // Constructors
    public Teacher(){}

    public Teacher(String firstName, String lastName, String email, String password, Blob profile) {
        super(firstName, lastName, email, password, profile, Role.ROLE_TEACHER);
        this.subjects = new ArrayList<>();
    }

    public Teacher(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, Role.ROLE_TEACHER);
        this.subjects = new ArrayList<>();

    }

    public Teacher(User t) {
        super(t);
    }

    // Include a constructor with parameters if needed

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void setOneSubject(Subject s){
        this.subjects.add(s);
    }

    //Others 
    @SuppressWarnings("unlikely-arg-type")
    public void deleteSubjectId(Long id){

        for (Subject subject : subjects) {
            if (subject.getId() == id) {
                subjects.remove(subjects);
            }
        }
    }

    // Add any additional methods needed for your logic
}
