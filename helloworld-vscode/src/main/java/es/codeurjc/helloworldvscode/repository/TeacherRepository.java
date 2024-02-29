package es.codeurjc.helloworldvscode.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.helloworldvscode.Entitys.Teacher;


public interface TeacherRepository extends JpaRepository<Teacher, Long>{
	Optional<Teacher> findByFirstName(String name);
	Optional<Teacher> findById(Long id);
} 