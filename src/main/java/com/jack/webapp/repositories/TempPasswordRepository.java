package com.jack.webapp.repositories;

import com.jack.webapp.domain.entities.TempPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TempPasswordRepository extends JpaRepository<TempPassword, Long> {
    TempPassword findValidTokenByUser(Long id);
    @Query(value = """
            select t from TempPassword t inner join UserEntity u\s
            on t.user.id = u.id\s
            where u.id = :id and ((t.expired = false and t.revoked = false) or t.used = false)\s
            """)
    TempPassword findAgainValid(Long id);
    Optional<TempPassword> findByToken(String token);
}
