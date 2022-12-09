package com.capstone.capstone.post.mapper;

import com.capstone.capstone.member.entity.Member;
import com.capstone.capstone.post.dto.PostDto;
import com.capstone.capstone.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    default Post postPostToPost(PostDto.Post requestBody) {
        Post post = new Post();
        Member member = new Member();

        member.setMemberId((long) requestBody.getUser_id());

        post.setPostTitle(requestBody.getPost_title());
        post.setPostContent(requestBody.getPost_content());
        post.setPostView(0);
        post.setPostLikeCount(0);
        post.setPostDislikeCount(0);
        post.setPostCommentCount(0);
        post.setMember(member);

        return post;
    }
    default Post postPatchToPost(PostDto.Patch requestBody) {
        Post post = new Post();
        Member member = new Member();

        member.setMemberId((long) requestBody.getUser_id());

        post.setId(requestBody.getId());
        post.setPostTitle(requestBody.getPost_title());
        post.setPostContent(requestBody.getPost_content());
        post.setPostView(0);
        post.setPostLikeCount(0);
        post.setPostDislikeCount(0);
        post.setPostCommentCount(0);
        post.setMember(member);
        post.setMember(member);

        return post;
    }

    default PostDto.Response postToPostResponse(Post post) {
        PostDto.Response response = new PostDto.Response();

        response.setId(post.getId());
        response.setPost_title(post.getPostTitle());
        response.setPost_content(post.getPostContent());
        response.setPost_view(post.getPostView());
        response.setPost_like_count(post.getPostLikeCount());
        response.setPost_dislike_count(post.getPostDislikeCount());
        response.setPost_comment_count(post.getPostCommentCount());
        response.setUser_id(post.getMember().getMemberId().intValue());
        response.setCreated_at(post.getCreatedAt().toString());
        response.setLast_modified_at(post.getModifiedAt().toString());
        response.setUser_name(post.getMember().getName());

        return response;
    }

    List<PostDto.Response> postsToPostResponses(List<Post> posts);
}
