package es.codeurjc.backend.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import es.codeurjc.backend.Entitys.Student;
import es.codeurjc.backend.Entitys.Subject;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void enviarCorreo(String destinatario, Student student, Subject subject) {
                 
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("Add me pls");
        mensaje.setText("Student Email:"+student.getEmail()+ "\r\n" +
                        "Class name: "+subject.getName());
        javaMailSender.send(mensaje);
    }


    
}

