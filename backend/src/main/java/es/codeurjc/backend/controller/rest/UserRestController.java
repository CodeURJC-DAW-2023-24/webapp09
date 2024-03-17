package es.codeurjc.backend.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import es.codeurjc.backend.model.*;
import es.codeurjc.backend.services.AdminService;
import es.codeurjc.backend.services.StudentService;
import es.codeurjc.backend.services.SubjectService;
import es.codeurjc.backend.services.TeacherService;
import es.codeurjc.backend.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users/")
public class UserRestController {

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @GetMapping("/subjects_registereduser")
    public List<Subject> getSubjectsRegisteredUser(Principal principal) {
        Optional<User> user = userService.getByEmail(principal.getName());
        List<Subject> subjects = new ArrayList<>();

        if (user.isPresent()) {
            if (user.get().getRoles().contains("TEACHER")) {
                Teacher teacher = teacherService.getTeacherByEmail(principal.getName());
                subjects = teacherService.getSubjectsByTeacherId(teacher.getId());
            } else if (user.get().getRoles().contains("STUDENT")) {
                Student student = studentService.getStudentByEmail(principal.getName());
                subjects = studentService.getSubjectsByStudentId(student.getId());
            }
        }

        return subjects;
    }

    @GetMapping("/redirection/{subjectId}")
    public String redirectURL(Principal principal, @PathVariable Long subjectId) {
        Optional<User> user = userService.getByEmail(principal.getName());
        String url = "";

        if (user.isPresent()) {
            if (user.get().getRoles().contains("STUDENT")) {
                url = "/subject_onesubj_student/" + subjectId;
            } else if (user.get().getRoles().contains("TEACHER")) {
                url = "/teachers/subject/" + subjectId + "/general-information";
            }
        }

        return ("redirect:" + url);
    }

    @GetMapping("/profile")
    public User getProfile(Principal principal) {
        Optional<User> userOptional = userService.getByEmail(principal.getName());
        User userDTO = new User();

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmail(user.getEmail());
            //userDTO.setRoles(user.getRoles());
            userDTO.setProfilePicture(user.getProfilePicture());
        }

        return userDTO;
    }

    @PutMapping("/editProfile")
    public ResponseEntity<String> editProfile(Principal principal, @RequestBody User userDTO) {
        Optional<User> userOptional = userService.getByEmail(principal.getName());

        if (userOptional.isPresent()) {
            User currentUser = userOptional.get();
            currentUser.setFirstName(userDTO.getFirstName());
            currentUser.setLastName(userDTO.getLastName());
            currentUser.setEmail(userDTO.getEmail());
            currentUser.setPassword(userDTO.getPassword());
            currentUser.setProfilePicture(userDTO.getProfilePicture());
            userService.setUser(currentUser);
            return ResponseEntity.ok("Profile updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

