package com.capstone.capstone.comment.controller;

import com.capstone.capstone.comment.dto.CommentDto;
import com.capstone.capstone.comment.entity.Comment;
import com.capstone.capstone.comment.mapper.CommentMapper;
import com.capstone.capstone.comment.service.CommentService;
import com.capstone.capstone.dto.MultiResponseDto;
import com.capstone.capstone.dto.SingleResponseDto;
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
@RequestMapping("/v11/comments")
@Validated
@Slf4j
public class CommentController {
    private final CommentService commentService;

    private final CommentMapper mapper;

    public CommentController(CommentService commentService, CommentMapper mapper) {
        this.commentService = commentService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postComment(@Valid @RequestBody CommentDto.Post requestBody) {
        Comment comment = mapper.commentPostToComment(requestBody);

        Comment createdComment = commentService.createComment(comment);
        CommentDto.Response response = mapper.commentToCommentResponse(createdComment);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response),
                HttpStatus.CREATED
        );
    }

    // update 미구현
    @PatchMapping("/{comment-id}")
    public ResponseEntity patchComment(
            @PathVariable("comment-id") @Positive int commentId,
            @Valid @RequestBody CommentDto.Patch requestBody) {
        requestBody.setId(commentId);
        Comment comment = commentService.updateComment(mapper.commentPatchToComment(requestBody));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.commentToCommentResponse(comment)),
                HttpStatus.OK
        );
    }

    // 옷 id로 검색
    @GetMapping("/cloths/{cloth-id}")
    public ResponseEntity getClothComments(@Positive @RequestParam int page,
                                           @Positive @RequestParam int size,
                                           @PathVariable("cloth-id") @Positive int clothId) {
        Page<Comment> pageComments = commentService.findClothComments(clothId, page-1, size);
        List<Comment> comments = pageComments.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.commentsToCommentResonses(comments), pageComments),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{comment-id}")
    public ResponseEntity deleteComment(@PathVariable("comment-id") @Positive int commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
