package com.capstone.capstone.comment.service;

import com.capstone.capstone.cloth.service.ClothService;
import com.capstone.capstone.comment.entity.Comment;
import com.capstone.capstone.comment.repository.CommentRepository;
import com.capstone.capstone.exception.BusinessLogicException;
import com.capstone.capstone.exception.ExceptionCode;
import com.capstone.capstone.member.service.MemberService;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CommentService {
    private final MemberService memberService;
    private final ClothService clothService;
    private final CommentRepository commentRepository;

    public CommentService(MemberService memberService, ClothService clothService, CommentRepository commentRepository) {
        this.memberService = memberService;
        this.clothService = clothService;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Comment createComment(Comment comment) {
        long member_id = comment.getMember().getMemberId();
        int cloth_id = comment.getCloth().getId();

        if (!commentRepository.existsById(comment.getId())) {
            // 댓글 작성
            Comment saveComment = commentRepository.save(comment);
            return saveComment;
        }

        return null;
    }

    // 수정 필요
    @Transactional
    public Comment updateComment(Comment comment) {
        Comment findComment = findVerifiedComment(comment.getId());

        return commentRepository.save(findComment);
    }

    @Transactional
    public Comment findComment(int commentId) {
        return findVerifiedComment(commentId);
    }

    // 전체 목록(사용 안함)
    @Transactional
    public Page<Comment> findComments(int page, int size) {
        return commentRepository.findAll(PageRequest.of(page, size,
                Sort.by("CommentId").descending()));
    }

    // 특정 옷 전체 댓글 조회
    @Transactional
    public Page<Comment> findClothComments(int clothId, int page, int size) {
        List<Comment> commentList = commentRepository.findByCloth_Id(clothId);

        Pageable pageable = PageRequest.of(page, size);
        PagedListHolder pagedListHolder = new PagedListHolder(commentList);
        pagedListHolder.setPageSize(size);
        pagedListHolder.setPage(page);

        return new PageImpl<>(pagedListHolder.getPageList(), pageable, commentList.size());
    }

    @Transactional
    public void deleteComment(int commentId) {
        Comment findComment = findVerifiedComment(commentId);

        commentRepository.delete(findComment);
    }

    @Transactional(readOnly = true)
    public Comment findVerifiedComment(int commentId) {
        Optional<Comment> optionalComment =
                commentRepository.findById(commentId);
        Comment findComment =
                optionalComment.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        return findComment;
    }
}
