package com.capstone.capstone.vote.mapper;

import com.capstone.capstone.member.entity.Member;
import com.capstone.capstone.post.entity.Post;
import com.capstone.capstone.vote.dto.VoteDto;
import com.capstone.capstone.vote.entity.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VoteMapper {
    default Vote votePostToVote(VoteDto.Post requestBody) {
        Vote vote = new Vote();
        Member member = new Member();
        Post post = new Post();

        member.setMemberId(requestBody.getMember_id());
        post.setId(requestBody.getPost_id());

        vote.setVoteType(requestBody.getVote_type());
        vote.setPost(post);
        vote.setMember(member);

        return vote;
    }

    default VoteDto.Response voteToVoteResponse(Vote vote) {
        VoteDto.Response response = new VoteDto.Response();

        response.setId(vote.getId());
        response.setPost_id(vote.getPost().getId());
        response.setMember_id(vote.getMember().getMemberId());
        response.setVote_type(vote.getVoteType());

        return response;
    }

    List<VoteDto.Response> votesToVoteResponses(List<Vote> votes);
}
