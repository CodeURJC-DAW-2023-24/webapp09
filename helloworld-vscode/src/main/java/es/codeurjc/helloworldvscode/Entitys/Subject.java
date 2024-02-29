package es.codeurjc.helloworldvscode.Entitys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Subject {

    @Id
    @Column(name = "subjectId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(length = 1000000)
    private String description;

    @Lob
    @Column(length = 1000000)
    private String allInfo;
    
    private String gender;

    private String banner;

    private String name;

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
    @JsonBackReference
    private List<Teacher> teachers;


    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Exam> exams;


    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
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
        this.teachers = new ArrayList<>();
        this.forums = new ArrayList<>();
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
        //complete if it is necesary
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
    public Long getSubjectId() {
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

    public void setSubjectId(Long id) {
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

    public void setOneStudent(Student s){
        students.add(s);
    }

    public void setOneTeacher(Teacher t){
        teachers.add(t);
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }


    //Others
    public void deleteStudentId(Long id){
        
        for (Student student : students) {
            if (student.getStudentId() == id) {
                students.remove(student);
                break;
            }
        }
    }


    public void deleteTeacherId(Long id){
        
        for (Teacher teacher : teachers) {
            if (teacher.getTeacherId() == id) {
                teachers.remove(teacher);
                break;
            }
        }
    }

}
