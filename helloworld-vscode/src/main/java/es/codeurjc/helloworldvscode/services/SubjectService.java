package es.codeurjc.helloworldvscode.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import es.codeurjc.helloworldvscode.Entitys.Subject;
import es.codeurjc.helloworldvscode.repository.SubjectRepository;

public class SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    public List<Subject> findAll(){
        return subjectRepository.findAll();
    }
}
