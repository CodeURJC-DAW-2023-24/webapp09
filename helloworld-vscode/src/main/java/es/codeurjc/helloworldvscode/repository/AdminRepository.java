package es.codeurjc.helloworldvscode.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.helloworldvscode.Entitys.Teacher;


public interface AdminRepository extends JpaRepository<Teacher, Long>{

}