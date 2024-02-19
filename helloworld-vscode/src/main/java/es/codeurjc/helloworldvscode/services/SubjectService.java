package es.codeurjc.helloworldvscode.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.repository.SubjectRepository;

@Service
public class SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    public List<Subject> findAll(){
        return subjectRepository.findAll();
    }

    public Optional<Subject> unique(Long id){
        return subjectRepository.findSubjectById(id);
    }
}
