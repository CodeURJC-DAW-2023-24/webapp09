package es.codeurjc.helloworldvscode.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.helloworldvscode.Entitys.Admin;


public interface AdminRepository extends JpaRepository<Admin, Long>{

}