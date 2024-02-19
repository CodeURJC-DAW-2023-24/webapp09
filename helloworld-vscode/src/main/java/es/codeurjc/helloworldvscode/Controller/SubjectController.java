package es.codeurjc.helloworldvscode.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.repository.SubjectRepository;
import es.codeurjc.helloworldvscode.services.SubjectService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class SubjectController {

    @Autowired
	    private SubjectRepository subjectsList;

    @PostConstruct
	public void init() {
        Subject s1 = new Subject("Estructuras de Datos", "La asignatura introduce estructuras de datos clásicas con enfoque en TAD y programación. Los alumnos aprenderán propiedades, funcionamiento e implementaciones para resolver problemas.", "Las estructuras de datos y algoritmos son fundamentales en Informática. Se enseñan en el segundo cuatrimestre del primer curso, continuando la asignatura de Introducción a la Programación."); 
        Subject s2 = new Subject("Desarrollo de Aplicaciones Web", "La asignatura ofrece una visión general del desarrollo de aplicaciones web para diversos dispositivos. Cubre características esenciales, tecnologías, arquitecturas y protocolos comunes.", "Las aplicaciones web son vitales en informática. La asignatura enseña el desarrollo web para crear aplicaciones profesionales.");
        Subject s3 = new Subject("Inteligencia Artificial", "La asignatura explora los fundamentos y aplicaciones de la inteligencia artificial. Los estudiantes estudiarán algoritmos de búsqueda, aprendizaje automático, lógica difusa y sistemas expertos.", "Proporciona una visión general de los fundamentos y aplicaciones de la IA. El objetivo es comprender y aplicar técnicas avanzadas de IA en diferentes campos.");
        Subject s4 = new Subject("Bases de Datos", "Los estudiantes aprenderán sobre diseño, implementación y optimización de bases de datos. Se explorarán temas como SQL, modelado de datos y gestión de transacciones.", "Bases de Datos introduce los conceptos esenciales de las bases de datos relacionales y no relacionales. Los estudiantes adquieren habilidades en diseño, implementación y gestión de bases de datos para aplicaciones modernas.");
        subjectsList.save(s1);
        subjectsList.save(s2);
        subjectsList.save(s3);
        subjectsList.save(s4);
    }    
}
