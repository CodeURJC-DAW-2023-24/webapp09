package es.codeurjc.helloworldvscode.repository;

import es.codeurjc.helloworldvscode.Entitys.Forum;
import org.springframework.data.jpa.repository.JpaRepository;




public interface ForumRepository extends JpaRepository<Forum, Long>{

}