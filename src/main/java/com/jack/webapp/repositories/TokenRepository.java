package com.jack.webapp.repositories;

import com.jack.webapp.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query(value = """
            select t from Token t inner join UserEntity u\s
            on t.user.id = u.id\s
            where u.id = :id and (t.annulled = false)\s
            """)
    List<Token> findAllValidTokenByCustomer(Long id);

    Optional<Token> findByToken(String token);
}
