package es.codeurjc.helloworldvscode.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.codeurjc.helloworldvscode.Entitys.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByFirstName(String name);
    Optional<User> findById(Long id);
    
}
