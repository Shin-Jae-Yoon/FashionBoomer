package com.capstone.capstone.vote.controller;

import com.capstone.capstone.dto.SingleResponseDto;
import com.capstone.capstone.post.entity.Post;
import com.capstone.capstone.post.service.PostService;
import com.capstone.capstone.vote.dto.VoteDto;
import com.capstone.capstone.vote.entity.Vote;
import com.capstone.capstone.vote.mapper.VoteMapper;
import com.capstone.capstone.vote.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/v11/votes")
@Validated
@Slf4j
public class VoteController {
    private final VoteService voteService;
    private final PostService postService;
    private final VoteMapper voteMapper;

    public VoteController(VoteService voteService, PostService postService, VoteMapper voteMapper) {
        this.voteService = voteService;
        this.postService = postService;
        this.voteMapper = voteMapper;
    }

    // voteType에 따라 해당 게시글(답글)의 추천 수 수정
    @PostMapping
    public ResponseEntity postVote(@Valid @RequestBody VoteDto.Post requestBody) {
        Vote vote = voteMapper.votePostToVote(requestBody);

        Vote createdVote = voteService.createVote(vote);

        return new ResponseEntity<>(
                new SingleResponseDto<>(voteMapper.voteToVoteResponse(createdVote))
                , HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity deleteVote(@Positive @RequestParam long memberId,
                                     @Positive @RequestParam int postId) {
        // 추천수 수정
        Post post = postService.findPost(postId);
        post.setPostView(post.getPostView() - voteService.findVerifiedVote(memberId, postId).getVoteType());
        postService.updatePost(post);

        voteService.deleteVote(memberId, postId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

