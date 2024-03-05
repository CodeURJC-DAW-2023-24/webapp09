package es.codeurjc.helloworldvscode.repository;

import es.codeurjc.helloworldvscode.Entitys.Subject;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    public Optional<Subject> findSubjectById(long id);
    // public Optional<Subject> findSubjectByName(String name);
    // public List<Subject> findSubjectsByGender(String gender); //ALG
    List<Subject> findByGender(String gender);

    Optional<Subject> findByName(String name);

    // NO TIENE SENTIDO
    @SuppressWarnings("null")
    Optional<Subject> findById(Long subjectId);

    @Query("SELECT s.gender, COUNT(s) FROM Subject s GROUP BY s.gender")
    List<Object[]> countSubjectsByGender();
}
