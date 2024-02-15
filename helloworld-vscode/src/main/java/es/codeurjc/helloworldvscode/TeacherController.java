package es.codeurjc.helloworldvscode;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.net.URI;
import java.util.Collection;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.helloworldvscode.Entitys.Teacher;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	private TeacherRepository teacherRepository;

	@PostConstruct
	public void init() {

		Teacher t1 = new Teacher("Pepe","Franco","pfespania@gmail.com","vivaespaña");
		Teacher t2 = new Teacher("Belen","Esteban","salavame@gmail.com","andreita");

		teacherRepository.save(t1);
		teacherRepository.save(t2);

	}
	
	@GetMapping("/")
	public Collection<Teacher> getItems() {
		return (Collection<Teacher>) teacherRepository.findAll();
	}

	@GetMapping("/{id}")
	public Teacher getItem(@PathVariable long id) {

		return teacherRepository.findById(id).orElseThrow();
	}

	@SuppressWarnings("null")
	@PostMapping("/")
	public ResponseEntity<Teacher> createItem(@RequestBody Teacher teacher) {

		teacherRepository.save(teacher);

		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(teacher.getId()).toUri();

		return ResponseEntity.created(location).body(teacher);
	}

	@PutMapping("/{id}")
	public Teacher replaceItem(@PathVariable long id, @RequestBody Teacher newteacherRepository) {

		teacherRepository.findById(id).orElseThrow();

		newteacherRepository.setId(id);
		teacherRepository.save(newteacherRepository);
			
		return newteacherRepository;
	}

	@DeleteMapping("/{id}")
	public Teacher deleteItem(@PathVariable long id) {

		Teacher item = teacherRepository.findById(id).orElseThrow();

		teacherRepository.deleteById(id);
		
		return item;
	}
}