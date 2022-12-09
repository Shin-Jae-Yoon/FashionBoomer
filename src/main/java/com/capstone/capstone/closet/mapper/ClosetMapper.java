package com.capstone.capstone.closet.mapper;

import com.capstone.capstone.closet.dto.ClosetDto;
import com.capstone.capstone.closet.entity.Closet;
import com.capstone.capstone.cloth.entity.Cloth;
import com.capstone.capstone.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClosetMapper {
    default Closet closetPostToCloset(ClosetDto.Post requestBody) {
        Closet closet = new Closet();
        Member member = new Member();
        Cloth cloth = new Cloth();

        member.setMemberId(requestBody.getUser_id());
        cloth.setId(requestBody.getCloth_id());

        closet.setMember(member);
        closet.setCloth(cloth);

        return closet;
    }
    default ClosetDto.Response closetToClosetResponse(Closet closet) {
        ClosetDto.Response response = new ClosetDto.Response();

        if (closet == null) return response;

        response.setId(closet.getId());
        response.setUser_id(closet.getMember().getMemberId());
        response.setCloth_id(closet.getCloth().getId());

        return response;
    }
    List<ClosetDto.Response> closetsToClosetResponses(List<Closet> closets);
}
