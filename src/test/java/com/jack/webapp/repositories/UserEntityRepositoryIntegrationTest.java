package com.jack.webapp.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserEntityRepositoryIntegrationTest {
//    private AuthorRepository underTest;
//
//    @Autowired
//    public void AuthorRepositoryIntegrationTest(AuthorRepository underTest) {
//        this.underTest = underTest;
//    }
//
//    @Test
//    public void testThatAuthorCanBeCreatedAndREcalled() {
//        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
//        underTest.save(authorEntity);
//        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(authorEntity);
//    }
//
//    @Test
//    public void testThatMultipleAuthorsCanBeCreatedAndREcalled() {
//        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
//        underTest.save(authorEntityA);
//        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();
//        underTest.save(authorEntityB);
//        AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC();
//        underTest.save(authorEntityC);
//
//        Iterable<AuthorEntity> result = underTest.findAll();
//        assertThat(result)
//                .hasSize(3)
//                .containsExactly(authorEntityA, authorEntityB, authorEntityC); //assert chain
//    }
//
//    @Test
//    public void testThatAuthorCanBeUpdated() {
//        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
//        underTest.save(authorEntity);
//        authorEntity.setName("UPDATED");
//        underTest.save(authorEntity);
//        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(authorEntity);
//    }
//
//    @Test
//    public void testThatAuthorCanBeDeleted() {
//        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
//        underTest.save(authorEntity);
//        underTest.deleteById(authorEntity.getId());
//        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
//        assertThat(result).isEmpty();
//    }
//
//    @Test
//    public void testThatGetAuthorsWithAgeLessThan() {
//        AuthorEntity testAuthorAEntity = TestDataUtil.createTestAuthorEntityA();
//        underTest.save(testAuthorAEntity);
//        AuthorEntity testAuthorBEntity = TestDataUtil.createTestAuthorB();
//        underTest.save(testAuthorBEntity);
//        AuthorEntity testAuthorCEntity = TestDataUtil.createTestAuthorC();
//        underTest.save(testAuthorCEntity);
//        Iterable<AuthorEntity> results = underTest.ageLessThan(50); // spring data jpa is able to implement sql queries based only on the names of your methods
//        assertThat(results).containsExactly(testAuthorBEntity, testAuthorCEntity);
//
//    }
//
//    @Test
//    public void testThatGetAuthorsWithAgeGreaterThan() {
//        AuthorEntity testAuthorAEntity = TestDataUtil.createTestAuthorEntityA();
//        underTest.save(testAuthorAEntity);
//        AuthorEntity testAuthorBEntity = TestDataUtil.createTestAuthorB();
//        underTest.save(testAuthorBEntity);
//        AuthorEntity testAuthorCEntity = TestDataUtil.createTestAuthorC();
//        underTest.save(testAuthorCEntity);
//        Iterable<AuthorEntity> result = underTest.findAuthorsWithAgeGreaterThan(50);
//        assertThat(result).containsExactly(testAuthorAEntity);
//    }

}
