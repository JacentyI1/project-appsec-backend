package com.jack.webapp.repositories;

import com.jack.webapp.domain.entities.PostEntity;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity, String> {
}
