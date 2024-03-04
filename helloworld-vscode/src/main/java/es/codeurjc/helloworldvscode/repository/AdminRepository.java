package es.codeurjc.helloworldvscode.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.helloworldvscode.Entitys.Admin;
import es.codeurjc.helloworldvscode.Entitys.User;


public interface AdminRepository extends JpaRepository<Admin, Long>{
    Optional<Admin> findByEmail(String email);

}