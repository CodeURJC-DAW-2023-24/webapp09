package es.codeurjc.helloworldvscode.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.repository.SubjectRepository;

@Service
public class SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public Optional<Subject> unique(Long id) {
        return subjectRepository.findSubjectById(id);
    }

    public List<Subject> getSubjects(Pageable pageable) {
        Page<Subject> subjectPage = subjectRepository.findAll(pageable);
        return subjectPage.getContent();
    }

    public Page<Subject> findAll(Pageable pageable) {
        return subjectRepository.findAll(pageable);
    }

    public Subject getSubjectById(Long studentId) {
        // Retorna el estudiante o lanza una excepción si no se encuentra
        return subjectRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + studentId));
    }
}
