package es.codeurjc.helloworldvscode.EntitysRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.helloworldvscode.Entitys.Teacher;


public interface TeacherRepository extends JpaRepository<Teacher, Long>{
	
}