package es.codeurjc.helloworldvscode.repository;

import es.codeurjc.helloworldvscode.Entitys.Forum;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;




public interface ForumRepository extends JpaRepository<Forum, Long>{
    ArrayList<Forum> findBySubjectId(Long subjectId);
}