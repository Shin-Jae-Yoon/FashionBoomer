package com.capstone.capstone.vote.service;

import com.capstone.capstone.member.repository.MemberRepository;
import com.capstone.capstone.post.entity.Post;
import com.capstone.capstone.post.repository.PostRepository;
import com.capstone.capstone.post.service.PostService;
import com.capstone.capstone.vote.entity.Vote;
import com.capstone.capstone.vote.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PostService postService;

    public VoteService(VoteRepository voteRepository, MemberRepository memberRepository, PostRepository postRepository, PostService postService) {
        this.voteRepository = voteRepository;
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
        this.postService = postService;
    }

    /** 추천(비추천) 생성 */
    public Vote createVote(Vote vote) {
        long memberId = vote.getMember().getMemberId();
        int postId = vote.getPost().getId();

        if(voteRepository.existsByMember_MemberIdAndPost_Id(memberId, postId)) {
            Vote findVote = findVerifiedVote(memberId, postId);
            // 이미 추천했으면 무시
            if (findVote.getVoteType() == vote.getVoteType())   return vote;
            // 반대된 추천을 했다면 추천수 수정 & 이전 추천 삭제
            Post post = postService.findPost(vote.getPost().getId());

            // 추천 한 상황에서 비추천
            if (vote.getVoteType() == -1) {
                post.setPostLikeCount(post.getPostLikeCount()-1);
            }
            // 비추천 한 상황에서 추천
            else if (vote.getVoteType() == 1) {
                post.setPostDislikeCount(post.getPostDislikeCount()-1);
            }

            postService.updatePost(post);

            deleteVote(memberId, postId);
            return vote;
        }

        else if (!voteRepository.existsById(vote.getId())) {
            // 추천수 수정
            Post post = postService.findPost(vote.getPost().getId());

            // 추천
            if (vote.getVoteType() == 1) {
                post.setPostLikeCount(post.getPostLikeCount()+1);
            }
            // 비추천
            else if (vote.getVoteType() == -1) {
                post.setPostDislikeCount(post.getPostDislikeCount()+1);
            }

            postService.updatePost(post);

            vote.setMember(memberRepository.findById(memberId).get());
            vote.setPost(postRepository.findById(postId).get());

            Vote saveVote = voteRepository.save(vote);
            return saveVote;
        }
        return  findVerifiedVote(memberId, postId);
    }

    /** 추천(비추천) 삭제 */
    public void deleteVote(long memberId, int postId) {
        Vote findVote = findVerifiedVote(memberId, postId);

        voteRepository.delete(findVote);
    }

    /** id로 추천(비추천) get */
    @Transactional(readOnly = true)
    public Vote findVerifiedVote(int voteId) {
        Optional<Vote> optionalPost = voteRepository.findById(voteId);
        Vote findVote = optionalPost.orElseThrow();     // Exception code 추가 필요
        return findVote;
    }

    /** memberId, postId로 추천(비추천) get */
    @Transactional(readOnly = true)
    public Vote findVerifiedVote(long memberId, int postId) {
        Optional<Vote> optionalPost = voteRepository.findByMember_MemberIdAndPost_Id(memberId, postId);
        Vote findVote = optionalPost.orElseThrow();     // Exception code 추가 필요
        return findVote;
    }
}
