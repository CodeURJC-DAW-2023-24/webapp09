package es.codeurjc.helloworldvscode.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.helloworldvscode.Entitys.Admin;
import es.codeurjc.helloworldvscode.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    public Admin getAdminByEmail(String email) {
        // Retorna el estudiante o lanza una excepción si no se encuentra
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found with name " + email));
    }
    
}
