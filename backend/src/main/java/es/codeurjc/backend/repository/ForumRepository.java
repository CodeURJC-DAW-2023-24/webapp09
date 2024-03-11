package es.codeurjc.backend.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.backend.Entitys.Forum;




public interface ForumRepository extends JpaRepository<Forum, Long>{
    ArrayList<Forum> findBySubjectId(Long subjectId);
}