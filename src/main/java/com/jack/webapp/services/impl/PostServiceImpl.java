package com.jack.webapp.services.impl;

import com.jack.webapp.domain.entities.PostEntity;
import com.jack.webapp.repositories.PostRepository;
import com.jack.webapp.services.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }



    @Override
    public boolean isExists(Long post_id) {
        return postRepository.existsById(post_id.toString());
    }

    @Override
    public PostEntity createUpdatePost(Long post_id, PostEntity post) {
        post.setPostId(post_id);
        return postRepository.save(post);
    }

    @Override
    public List<PostEntity> findAll() {
        return StreamSupport.stream(postRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<PostEntity> findOne(Long post_id) {
        return postRepository.findById(post_id.toString());
    }

//    @Override
//    public PostEntity partialUpdate(String post_id, PostEntity postEntity) {
//        postEntity.setPostId(post_id);
//
//        return postRepository.findById(post_id).map(existingPost -> {
//                    Optional.ofNullable(postEntity.getPost()).ifPresent(existingPost::setPost);
//                    return postRepository.save(existingPost);
//                }
//        ).orElseThrow(()-> new RuntimeException("Book does not exist"));
//    }

    @Override
    public void delete(Long post_id) {
        postRepository.deleteById(post_id.toString());
    }

}
