package com.capstone.capstone.image.mapper;

import com.capstone.capstone.image.dto.ImageDto;
import com.capstone.capstone.image.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper {
    ImageDto.Response imageToImageResponse(Image image);
    List<ImageDto.Response> imagesToImageResponses(List<Image> images);
}
