package es.codeurjc.helloworldvscode.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.helloworldvscode.Entitys.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findFirstByFirstName(String name);
    @SuppressWarnings("null")
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    
}
