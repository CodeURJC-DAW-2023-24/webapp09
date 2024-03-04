package es.codeurjc.helloworldvscode.Entitys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import es.codeurjc.helloworldvscode.enumerate.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student extends User{

    @ManyToMany(mappedBy = "students")
    @JsonManagedReference
    private List<Subject> subjects;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<ExamStudent> examStudents;

    // Constructors
    public Student() {}

    public Student(String firstName, String lastName, String email, String password, Blob profile) {
        super(firstName, lastName, email, password, profile, Role.ROLE_STUDENT);
        this.subjects = new ArrayList<>();
        this.examStudents = new ArrayList<>();

    }

    public Student(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, Role.ROLE_STUDENT);
        this.subjects = new ArrayList<>();
        this.examStudents = new ArrayList<>();
    }



    public List<Subject> getSubjects() {
        return subjects;
    }

    public List<ExamStudent> getExamStudents() {
        return examStudents;
    }


    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void setExamStudents(List<ExamStudent> examStudents) {
        this.examStudents = examStudents;
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
}
