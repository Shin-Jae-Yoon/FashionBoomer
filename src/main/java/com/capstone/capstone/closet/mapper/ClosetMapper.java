package com.capstone.capstone.closet.mapper;

import com.capstone.capstone.closet.dto.ClosetDto;
import com.capstone.capstone.closet.entity.Closet;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClosetMapper {
    Closet closetPostToCloset(ClosetDto.Post requestBody);
    ClosetDto.Response closetToClosetResponse(Closet closet);
    List<ClosetDto.Response> closetsToClosetResponses(List<Closet> closets);
}
