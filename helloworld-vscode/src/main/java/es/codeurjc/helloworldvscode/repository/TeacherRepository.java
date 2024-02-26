package es.codeurjc.helloworldvscode.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.helloworldvscode.Entitys.Teacher;
import es.codeurjc.helloworldvscode.Entitys.User;


public interface TeacherRepository extends JpaRepository<Teacher, Long>{
	Optional<User> findByFirstName(String name);
} 