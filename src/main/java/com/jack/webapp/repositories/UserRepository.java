package com.jack.webapp.repositories;

import com.jack.webapp.domain.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
