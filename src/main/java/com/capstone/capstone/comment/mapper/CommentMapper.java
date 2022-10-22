package com.capstone.capstone.comment.mapper;

import com.capstone.capstone.cloth.entity.Cloth;
import com.capstone.capstone.comment.dto.CommentDto;
import com.capstone.capstone.comment.entity.Comment;
import com.capstone.capstone.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    default Comment commentPostToComment(CommentDto.Post requestBody) {
        Comment comment = new Comment();
        Member member = new Member();
        Cloth cloth = new Cloth();

        member.setMemberId(requestBody.getUser_id());
        cloth.setId(requestBody.getCloth_id());

        comment.setMember(member);
        comment.setCloth(cloth);
        comment.setComment(requestBody.getComment());

        return comment;
    }

    default Comment commentPatchToComment(CommentDto.Patch requestBody) {
        Comment comment = new Comment();
        Member member = new Member();
        Cloth cloth = new Cloth();

        member.setMemberId(requestBody.getUser_id());
        cloth.setId(requestBody.getCloth_id());

        comment.setId(requestBody.getId());
        comment.setMember(member);
        comment.setCloth(cloth);
        comment.setComment(requestBody.getComment());

        return comment;
    }

    default CommentDto.Response commentToCommentResponse(Comment comment) {
        CommentDto.Response response = new CommentDto.Response();

        if (comment == null) return response;

        response.setId(comment.getId());
        response.setUser_id(comment.getMember().getMemberId());
        response.setCloth_id(comment.getCloth().getId());

        return response;
    }

    List<CommentDto.Response> commentsToCommentResonses(List<Comment> comments);
}
