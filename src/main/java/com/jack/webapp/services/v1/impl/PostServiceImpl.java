package com.jack.webapp.services.v1.impl;

import com.jack.webapp.domain.entities.v1.PostEntity;
import com.jack.webapp.repositories.v1.PostRepository;
import com.jack.webapp.services.v1.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void savePost(PostEntity post) {

    }

    @Override
    public boolean isExists(String isbn) {
        return postRepository.existsById(isbn);
    }

    @Override
    public PostEntity createUpdatePost(String isbn, PostEntity post) {
        post.setPostId(isbn);
        return postRepository.save(post);
    }

    @Override
    public List<PostEntity> findAll() {
        return StreamSupport.stream(postRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<PostEntity> findOne(String isbn) {
        return postRepository.findById(isbn);
    }

    @Override
    public PostEntity partialUpdate(String isbn, PostEntity postEntity) {
        postEntity.setPostId(isbn);

        return postRepository.findById(isbn).map(existingBook -> {
                    Optional.ofNullable(postEntity.getPost()).ifPresent(existingBook::setPost);
                    return postRepository.save(existingBook);
                }
        ).orElseThrow(()-> new RuntimeException("Book does not exist"));
    }

    @Override
    public void delete(String isbn) {
        postRepository.deleteById(isbn);
    }

//    @Override
//    public List<PostEntity> findAll(Pageable pageable) {
//
//    }
}
