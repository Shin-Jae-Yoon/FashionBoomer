package com.capstone.capstone.post.controller;

import com.capstone.capstone.dto.MultiResponseDto;
import com.capstone.capstone.dto.SingleResponseDto;
import com.capstone.capstone.post.dto.PostDto;
import com.capstone.capstone.post.entity.Post;
import com.capstone.capstone.post.mapper.PostMapper;
import com.capstone.capstone.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v11/posts")
@Validated
@Slf4j
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;

    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @PostMapping
    public ResponseEntity postPost(@Valid @RequestBody PostDto.Post requestBody) {
        Post post = postMapper.postPostToPost(requestBody);

        Post createdPost = postService.createPost(post);

        return new ResponseEntity<>(
                new SingleResponseDto<>(postMapper.postToPostResponse(createdPost)), HttpStatus.CREATED
        );
    }

    @PatchMapping("/{PostId}")
    public ResponseEntity patchPost(
            @PathVariable("PostId") @Positive int postId,
            @Valid @RequestBody PostDto.Patch requestBody) {
        requestBody.setId(postId);
        Post post = postService.updatePost(postMapper.postPatchToPost(requestBody));

        return new ResponseEntity<>(
                new SingleResponseDto<>(postMapper.postToPostResponse(post)),
                HttpStatus.OK);
    }

    /** 단일 게시글 get */
    // 게시글 클릭이라고 생각하고 조회수 1 증가
    @GetMapping("/{PostId}")
    public ResponseEntity getPost(@PathVariable("PostId") @Positive int postId) {
        Post post = postService.findPost(postId);

        // 조회수 1 증가
        post.setPostView(post.getPostView()+1);
        Post savedPost = postService.updatePostView(post);

        return new ResponseEntity<>(
                new SingleResponseDto<>(postMapper.postToPostResponse(savedPost)), HttpStatus.OK
        );
    }

    /** 특정 멤버의 게시글 목록 get */
    @GetMapping("/members/{memberId}")
    public ResponseEntity getMemberPosts(@PathVariable("memberId") @Positive int memberId,
                                   @Positive @RequestParam int page,
                                   @Positive @RequestParam int size) {
        Page<Post> postPage = postService.findMemberPosts(memberId, page-1, size);
        List<Post> posts = postPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(postMapper.postsToPostResponses(posts), postPage), HttpStatus.OK
        );
    }

    /** 게시글 목록 get */
    @GetMapping
    public ResponseEntity getPosts(@Positive @RequestParam int page,
                                   @Positive @RequestParam int size) {
        Page<Post> postPage = postService.findPostPosts(0, page-1, size);
        List<Post> posts = postPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(postMapper.postsToPostResponses(posts), postPage), HttpStatus.OK
        );
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{PostId}")
    public ResponseEntity deletePost(@PathVariable("PostId") @Positive int postId) {
        postService.deletePost(postId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
