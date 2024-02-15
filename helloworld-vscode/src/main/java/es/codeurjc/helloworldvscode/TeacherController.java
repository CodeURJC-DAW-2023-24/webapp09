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
@RequestMapping("/teachers/")
public class TeacherController {

	@Autowired
	private TeacherRepository teacherList;

	@PostConstruct
	public void init() {

		Teacher t1 = new Teacher("Pepe","Franco","pfespania@gmail.com","vivaespaña");
		Teacher t2 = new Teacher("Belen","Esteban","salavame@gmail.com","andreita");

		teacherList.save(t1);
		teacherList.save(t2);

	}
	
	@GetMapping("/")
	public Collection<Teacher> getItems() {
		return teacherList.findAll();
	}

	@GetMapping("/{id}")
	public Teacher getItem(@PathVariable long id) {

		return teacherList.findById(id).orElseThrow();
	}

	@SuppressWarnings("null")
	@PostMapping("/")
	public ResponseEntity<Teacher> createItem(@RequestBody Teacher teacher) {

		teacherList.save(teacher);

		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(teacher.getId()).toUri();

		return ResponseEntity.created(location).body(teacher);
	}

	@PutMapping("/{id}")
	public Teacher replaceItem(@PathVariable long id, @RequestBody Teacher newteacherRepository) {

		teacherList.findById(id).orElseThrow();

		newteacherRepository.setId(id);
		teacherList.save(newteacherRepository);
			
		return newteacherRepository;
	}

	@DeleteMapping("/{id}")
	public Teacher deleteItem(@PathVariable long id) {

		Teacher item = teacherList.findById(id).orElseThrow();

		teacherList.deleteById(id);
		
		return item;
	}
}