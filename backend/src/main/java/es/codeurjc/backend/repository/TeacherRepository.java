package es.codeurjc.backend.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.backend.model.Teacher;


public interface TeacherRepository extends JpaRepository<Teacher, Long>{
	Optional<Teacher> findByFirstName(String name);

	Optional<Teacher> findTeacherByLastNameAndFirstName(String firstName, String lastName);
    Optional<Teacher> findByEmail(String email);
    ArrayList<Teacher> findAllBySubjectsId(Long subjectId);

} 