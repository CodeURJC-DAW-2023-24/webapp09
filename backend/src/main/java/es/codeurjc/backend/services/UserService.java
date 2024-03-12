package es.codeurjc.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<User> getByEmail(String email){
        return userRepository.findByEmail(email);
    }


    @SuppressWarnings("null")
    public void setUser(User user){
        userRepository.save(user);
    }

    
}