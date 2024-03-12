package es.codeurjc.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.backend.model.Forum;
import es.codeurjc.backend.repository.ForumRepository;

@Service
public class ForumService {

    @Autowired
    ForumRepository forumRepository;

    @SuppressWarnings("null")
    public void setForum(Forum forum){
        forumRepository.save(forum);
    }

    public List<Forum> getBySubjectId(Long subejctId){
        return forumRepository.findBySubjectId(subejctId);
    }

    @SuppressWarnings("null")
    public void deleteById(Long forumId){
        forumRepository.deleteById(forumId);
    }

}
