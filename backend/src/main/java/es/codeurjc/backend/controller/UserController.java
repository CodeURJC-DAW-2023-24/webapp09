package es.codeurjc.backend.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import es.codeurjc.backend.model.*;

import es.codeurjc.backend.services.AdminService;
import es.codeurjc.backend.services.StudentService;
import es.codeurjc.backend.services.SubjectService;
import es.codeurjc.backend.services.TeacherService;
import es.codeurjc.backend.services.UserService;

import org.springframework.ui.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UserController {

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

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/logout")
    public String showLogout() {
        return "redirect:/login";
    }

    @GetMapping("/subjects_registereduser")
    public ModelAndView showSubjects(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        ModelAndView modelAndView = new ModelAndView("subjects_registereduser");
        Optional<User> user = userService.getByEmail(principal.getName());

        if (user.get().getRoles().contains("TEACHER")) {
            modelAndView.addObject("isStudent", false);
            Teacher teacher = teacherService.getTeacherByEmail(principal.getName());
            modelAndView.addObject("user", teacher);

        } else if (user.get().getRoles().contains("STUDENT")) {
            modelAndView.addObject("isStudent", true);
            Student student = studentService.getStudentByEmail(principal.getName());
            List<Subject> recommendedSubjects = subjectService.recommendSubjects(student);
            modelAndView.addObject("user", student);
            modelAndView.addObject("recommendedSubjects", recommendedSubjects);

        } else if (user.get().getRoles().contains("ADMIN")) {
            modelAndView.addObject("isStudent", false);
            Admin admin = adminService.getAdminByEmail(principal.getName());
            modelAndView.addObject("user", admin);
        }

        return modelAndView;
    }

    @GetMapping("/redirection/{subjectId}")
    public String redirectURL(HttpServletRequest request, @PathVariable Long subjectId) {
        Principal principal = request.getUserPrincipal();
        Optional<User> user = userService.getByEmail(principal.getName());
        String url = "";

        if (user.get().getRoles().contains("STUDENT")) {
            url = "/subject_onesubj_student/" + subjectId;

        } else if (user.get().getRoles().contains("TEACHER")) {
            url = "/teachers/subject/" + subjectId + "/general-information";
        }

        return ("redirect:" + url);
    }

    @RequestMapping("/loginerror")
    public String loginerror() {
        return "loginerror";
    }

    @GetMapping("/sign-up")
    public ModelAndView showSignup(@RequestParam String error) {

        String errorM = "";

        if (error.equals("repeat")) {
            errorM = "Email do not acept!";
        } else if (error.equals("password")) {
            errorM = "Passwords do not match!";
        } else if (error.equals("email")) {
            errorM = "Emails do not match!";
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signup");
        modelAndView.addObject("errorM", errorM);

        return modelAndView;
    }

    @PostMapping("/sign-up")
    public void showSignup(HttpServletRequest request, HttpServletResponse response,
            Model model,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String confirmEmail,
            @RequestParam String password,
            @RequestParam String confirmPassword) throws IOException {

        if (studentService.emailRepeat(email)) {

            // Same password?
            if (!email.equals(confirmEmail)) {
                response.sendRedirect("/sign-up?error=email");
            }
            if (!password.equals(confirmPassword)) {
                response.sendRedirect("/sign-up?error=password");
            }

            // Save new user
            Student newStudent = new Student(firstName, lastName, email, password);
            studentService.setStudent(newStudent);
            response.sendRedirect("/login");

        } else {
            response.sendRedirect("sign-up?error=repeat");
        }

    }

    @GetMapping("/profile")
    public ModelAndView personalArea(Model model, HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView("profile");

        Principal principal = request.getUserPrincipal();
        Optional<User> userOptional = userService.getByEmail(principal.getName());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            boolean hasImage = !(user.getProfilePicture() == null);
            model.addAttribute("hasImage", hasImage);

            String firstName = user.getFirstName();
            model.addAttribute("firstName", firstName);

            String lastName = user.getLastName();
            model.addAttribute("lastName", lastName);

            String email = user.getEmail();
            model.addAttribute("email", email);

            boolean isStudent = request.isUserInRole("STUDENT");
            model.addAttribute("isStudent", isStudent);

        }

        model.addAttribute("username", request.getUserPrincipal().getName());
        String role = "UNKNOWN"; // Por defecto

        String username = request.getUserPrincipal().getName();
        /*
         * if (request.isUserInRole("STUDENT")) {
         * role = "Student";
         * Student student = studentService.getStudentByEmail(username);
         * } else if (request.isUserInRole("TEACHER")) {
         * role = "Teacher";
         * Teacher teacher = teacherService.getByEmail(username);
         * }
         */

        List<Subject> subjects = new ArrayList<>();

        if (request.isUserInRole("STUDENT")) {
            // Si se encuentra un estudiante, obtener las asignaturas asociadas
            Student student = studentService.getStudentByEmail(username);
            role = "Student";
            subjects = studentService.getSubjectsByStudentId(student.getId());
            modelAndView.addObject("subjects", subjects);

        } else if (request.isUserInRole("TEACHER")) {
            // Si se encuentra un profesor, obtener las asignaturas asociadas
            Teacher teacher = teacherService.getByEmail(username);
            role = "Teacher";
            subjects = teacherService.getSubjectsByTeacherId(teacher.getId());
            modelAndView.addObject("subjects", subjects);

        } else if (request.isUserInRole("ADMIN")) {
            // Si se encuentra un profesor, obtener las asignaturas asociadas
            role = "Admin";
        }

        model.addAttribute("role", role);

        // Crear el modelo y agregar las asignaturas
        return modelAndView;
    }

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            model.addAttribute("logged", true);
        } else {
            model.addAttribute("logged", false);
        }
    }

    @GetMapping("/editProfile")
    public String showEditProfile() {
        return "editProfile";
    }

    @PostMapping("/editProfile")
    public String editarPerfil(HttpServletRequest request,
            Model model,
            @RequestParam("firstName") Optional<String> firstName,
            @RequestParam("lastName") Optional<String> lastName,
            @RequestParam("email") Optional<String> email,
            @RequestParam("password") Optional<String> password,
            @RequestParam("file") Optional<MultipartFile> file) throws ServletException, IOException {

        Principal principal = request.getUserPrincipal();
        Optional<User> user = userService.getByEmail(principal.getName());
        if (user.isPresent()) {
            User currentUser = user.get();
            currentUser.setFirstName(firstName.orElse(currentUser.getFirstName()));
            currentUser.setLastName(lastName.orElse(currentUser.getLastName()));
            currentUser.setEmail(email.orElse(currentUser.getEmail()));
            currentUser.setPassword(password.orElse(currentUser.getPassword()));
            if (file.isPresent()) {
                try {
                    byte[] foto = file.get().getBytes();
                    currentUser.setProfilePicture(foto);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            userService.setUser(currentUser);

            List<GrantedAuthority> authorities = currentUser.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(currentUser.getEmail(), currentUser.getPassword(),
                            authorities));
        }
        return "redirect:/profile";
    }

    @SuppressWarnings("null")
    @GetMapping("/user/photo")
    public ResponseEntity<byte[]> getCurrentUserPhoto(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            Optional<User> userOptional = userService.getByEmail(principal.getName());
            if (userOptional.isPresent()) {
                byte[] imageBytes = userOptional.get().getProfilePicture();
                if (imageBytes != null && imageBytes.length > 0) {
                    return ResponseEntity
                            .ok()
                            .contentType(MediaType.IMAGE_JPEG) // Ajusta según el formato de tu imagen
                            .body(imageBytes);
                }
            }
        }
        // Si el usuario no tiene foto de perfil o no está logueado, retorna la imagen
        // predeterminada
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(loadDefaultImage());
    }

    private byte[] loadDefaultImage() {
        try {
            // Asegúrate de que el path a tu imagen predeterminada sea correcto
            Path path = Paths.get("src\\main\\resources\\static\\images\\imagenFotoPerfil.jpg");
            return Files.readAllBytes(path);
        } catch (IOException e) {
            // Log the exception and consider an appropriate fallback
            e.printStackTrace();
            return new byte[0];
        }
    }

}
