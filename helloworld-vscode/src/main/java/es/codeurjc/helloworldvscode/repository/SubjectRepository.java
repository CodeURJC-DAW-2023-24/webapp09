package es.codeurjc.helloworldvscode.repository;

import es.codeurjc.helloworldvscode.Entitys.Subject;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long>{
    public Optional<Subject> findSubjectById(long id);
    public Optional<Subject> findSubjectByName(String name);
    //public List<Subject> findSubjectsByGender(String gender); //ALG
    
}
