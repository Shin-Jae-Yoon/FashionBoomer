package com.capstone.capstone.post.service;

import com.capstone.capstone.member.repository.MemberRepository;
import com.capstone.capstone.post.entity.Post;
import com.capstone.capstone.post.repository.PostRepository;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    /** 게시글 생성 */
    @Transactional
    public Post createPost(Post post) {
        long memberId = post.getMember().getMemberId();

        if (!postRepository.existsById(post.getId())) {
            post.setMember(memberRepository.findById(memberId).get());
            Post savePost = postRepository.save(post);
            return savePost;
        }
        return null;
    }

    /** 게시글 수정 */
    @Transactional
    public Post updatePost(Post post) {
        Post findPost = findVerifiedPost(post.getId());

        // 제목, 내용만 수정 가능
        findPost.setPostTitle(post.getPostTitle());
        findPost.setPostContent(post.getPostContent());
        findPost.setPostCommentCount(post.getPostCommentCount());

        return postRepository.save(findPost);
    }

    /** 게시글 조회수 증가 */
    @Transactional
    public Post updatePostView(Post post) {
        Post findPost = findVerifiedPost(post.getId());

        // 제목, 내용만 수정 가능
        findPost.setPostView(post.getPostView());

        return postRepository.save(findPost);
    }

    /** 특정 게시글 하나만 get */
    @Transactional(readOnly = true)
    public Post findPost(int postId) {
        return findVerifiedPost(postId);
    }

    /** 게시글 목록 get */
    @Transactional
    public Page<Post> findPostPosts(int postId, int page, int size) {
        List<Post> postList = postRepository.findAll();

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        PagedListHolder pagedListHolder = new PagedListHolder(postList);
        pagedListHolder.setPageSize(size);
        pagedListHolder.setPage(page);

        return new PageImpl<>(pagedListHolder.getPageList(), pageable, postList.size());
    }

    /** memberId로 게시글 목록 가져오기 */
    @Transactional
    public Page<Post> findMemberPosts(int memberId, int page, int size) {
        List<Post> postList = postRepository.findByMember_MemberId(memberId);

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        PagedListHolder pagedListHolder = new PagedListHolder(postList);
        pagedListHolder.setPageSize(size);
        pagedListHolder.setPage(page);

        return new PageImpl<>(pagedListHolder.getPageList(), pageable, postList.size());
    }

    /** 게시글 삭제 */
    @Transactional
    public void deletePost(int postId) {
        Post findPost = findVerifiedPost(postId);

        postRepository.delete(findPost);
    }

    /** id로 게시글(답글) get */
    @Transactional(readOnly = true)
    public Post findVerifiedPost(int postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post findPost = optionalPost.orElseThrow();     // Exception code 추가 필요
        return findPost;
    }
}
