package com.jack.webapp.services;

import com.jack.webapp.domain.entities.PostEntity;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface PostService {

//    void savePost(PostEntity post);

    boolean isExists(Long post_id);

    PostEntity createUpdatePost(Long post_id, PostEntity postEntity);

    List<PostEntity> findAll();

    Optional<PostEntity> findOne(Long isbn);

//    PostEntity partialUpdate(String isbn, PostEntity bookEntity);

    void delete(Long post_id);

}
