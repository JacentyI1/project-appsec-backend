package com.jack.webapp.controllers;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

//    private Mapper<PostEntity, PostDto> postMapper;
//
//    private PostService postService;
//
//    public PostController(Mapper<PostEntity, PostDto> postMapper, PostService postService) {
//        this.postMapper = postMapper;
//        this.postService = postService;
//    }
//
//    @PutMapping(path = "/v1/posts/{isbn}")
//    public ResponseEntity<PostDto> createUpdatePost(
//            @PathVariable("isbn") String isbn,
//            @RequestBody PostDto postDto
//    ){
//        PostEntity postEntity = postMapper.mapFrom(postDto);
//        boolean postExists = postService.isExists(isbn);
//        PostEntity savedPostEntity = postService.createUpdatePost(isbn, postEntity);
//        PostDto savedPostDto = postMapper.mapTo(savedPostEntity);
//        if(postExists){
//            return new ResponseEntity<>(savedPostDto, HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>(savedPostDto, HttpStatus.CREATED);
//        }
//    }
//
//    @GetMapping(path = "/v1/posts")
//    public List<PostDto> listPosts() {
//        List<PostEntity> posts = postService.findAll();
//        return posts.stream().map(postMapper::mapTo).collect(Collectors.toList());
//    }
//
//    @GetMapping(path = "/v1/posts/{isbn}")
//    public ResponseEntity<PostDto> getBook(@PathVariable("isbn") String isbn) {
//        Optional<PostEntity> foundBook = postService.findOne(isbn);
//        return foundBook.map(bookEntity -> {
//            PostDto postDto = postMapper.mapTo(bookEntity);
//            return new ResponseEntity<>(postDto, HttpStatus.OK);
//        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @PatchMapping(path = "/v1/posts/{isbn}")
//    public ResponseEntity<PostDto> partialUpdateBook(
//            @PathVariable("isbn") String isbn,
//            @RequestBody PostDto postDto
//    ) {
//        boolean bookExists = postService.isExists(isbn);
//        if(!bookExists){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        PostEntity bookEntity = postMapper.mapFrom(postDto);
//        PostEntity updatedBookEntity = postService.partialUpdate(isbn, bookEntity);
//        return new ResponseEntity<>(
//                postMapper.mapTo(updatedBookEntity),
//                HttpStatus.OK
//        );
//    }
//    @DeleteMapping(path = "/v1/posts/{isbn}")
//    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn) {
//        postService.delete(isbn);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
}
