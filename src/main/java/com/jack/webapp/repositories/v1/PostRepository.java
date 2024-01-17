package com.jack.webapp.repositories.v1;

import com.jack.webapp.domain.entities.v1.PostEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PostRepository extends CrudRepository<PostEntity, String> {
    Optional<PostEntity> findAllByPostId(Long id);
}
