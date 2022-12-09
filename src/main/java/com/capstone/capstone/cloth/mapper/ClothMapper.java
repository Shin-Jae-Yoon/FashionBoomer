package com.capstone.capstone.cloth.mapper;

import com.capstone.capstone.cloth.dto.ClothDto;
import com.capstone.capstone.cloth.entity.Cloth;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClothMapper {
    ClothDto.Response clothToClothResponse(Cloth cloth);

    List<ClothDto.Response> clothesToClothesResponses(List<Cloth> clothes);
}
