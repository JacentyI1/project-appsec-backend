package com.jack.webapp.services.v1;

import com.jack.webapp.domain.entities.v1.PostEntity;

import java.util.List;
import java.util.Optional;

public interface PostService {

    void savePost(PostEntity post);

    boolean isExists(String isbn);

    PostEntity createUpdatePost(String isbn, PostEntity postEntity);

    List<PostEntity> findAll();

    Optional<PostEntity> findOne(String isbn);

    PostEntity partialUpdate(String isbn, PostEntity bookEntity);

    void delete(String isbn);


//    List<PostEntity> findAll(Pageable pageable);
}
