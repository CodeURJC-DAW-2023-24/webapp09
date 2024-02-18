package es.codeurjc.helloworldvscode.Entitys;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String allInfo;

    @ManyToMany
    @JoinTable(
            name = "subject_student",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students;

    @ManyToMany
    @JoinTable(
            name = "subject_teacher",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    
    private Set<Teacher> teachers;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exam> exams;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Forum> forums;

    // Constructors
    public Subject() {
    }

    public Subject(String name, String description, String allInfo) {
        this.name = name;
        this.description = description;
        this.allInfo = allInfo;
    }

    public Subject (Subject s){
        this.name = s.name;
        this.description = s.description;
        this.allInfo = s.allInfo;
        // COMPLETAR SI ES NECESARIO
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getAllInfo() {
        return allInfo;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public List<Forum> getForums() {
        return forums;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAllInfo(String allInfo) {
        this.allInfo = allInfo;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }

}
