package es.codeurjc.helloworldvscode.Entitys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, length = 1000000)
    private String description;
    @Column(nullable = false, length = 1000000)
    private String allInfo;
    @Column(nullable = false)
    private String gender;
    @Column
    private String banner;

    @ManyToMany
    @JoinTable(
            name = "subject_student",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    @JsonBackReference
    private List<Student> students;

    @ManyToMany
    @JoinTable(
            name = "subject_teacher",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "subject")//, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Exam> exams;

    @OneToMany(mappedBy = "subject")//, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Forum> forums;

    // Constructors
    public Subject() {
    }

    public Subject(String name, String description, String allInfo, String gender) {
        this.name = name;
        this.description = description;
        this.allInfo = allInfo;
        this.gender = gender;
        this.banner = "/images/blog-post-03.jpg";
        this.students = new ArrayList<>();
        this.exams = new ArrayList<>();
    }

    public Subject(String name, String description, String allInfo, String gender, String banner) {
        this.name = name;
        this.description = description;
        this.allInfo = allInfo;
        this.gender = gender;
        this.banner = banner;
        this.students = new ArrayList<>();
        this.exams = new ArrayList<>();

    }

    public Subject (Subject s){
        this.name = s.name;
        this.description = s.description;
        this.allInfo = s.allInfo;
        this.gender = s.gender;
        this.banner = s.banner;
        // COMPLETAR SI ES NECESARIO
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
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

    public List<Student> getStudents() {
        return students;
    }

    public List<Teacher> getTeachers() {
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

    public String getGender() {
        return gender;
    }

    // Setters
    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAllInfo(String allInfo) {
        this.allInfo = allInfo;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }

}
