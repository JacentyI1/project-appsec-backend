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
public class PostEntityRepositoryIntegrationClass {
//
//    private BookRepository underTest;
//
//    @Autowired
//    public PostEntityRepositoryIntegrationClass(BookRepository underTest){
//        this.underTest = underTest;
//    }
//
//    @Test
//    public void testThatBookCanBeCreatedAndRecalled() {
//        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
//        BookEntity bookEntity = TestDataUtil.createTestBook(authorEntity);
//        underTest.save(bookEntity);
//        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(bookEntity);
//    }
//
//    @Test
//    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
//        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
//
//        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA(authorEntity);
//        underTest.save(bookEntityA);
//
//        BookEntity bookEntityB = TestDataUtil.createTestBookB(authorEntity);
//        underTest.save(bookEntityB);
//
//        BookEntity bookEntityC = TestDataUtil.createTestBookC(authorEntity);
//        underTest.save(bookEntityC);
//
//        Iterable<BookEntity> result = underTest.findAll();
//        assertThat(result)
//                .hasSize(3)
//                .containsExactly(bookEntityA, bookEntityB, bookEntityC);
//
//    }
//
//    @Test
//    public void testThatBookCanBeUpdated() {
//        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
//
//        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA(authorEntity);
//        underTest.save(bookEntityA);
//
//        bookEntityA.setTitle("UPDATED");
//        underTest.save(bookEntityA);
//
//        Optional<BookEntity> result = underTest.findById(bookEntityA.getIsbn());
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(bookEntityA);
//    }
//
//    @Test
//    public void testThatBookCanBeDeleted() {
//        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
//
//        BookEntity bookEntity = TestDataUtil.createTestBook(authorEntity);
//        underTest.save(bookEntity);
//
//        underTest.deleteById(bookEntity.getIsbn());
//
//        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
//        assertThat(result).isEmpty();
//    }
}
