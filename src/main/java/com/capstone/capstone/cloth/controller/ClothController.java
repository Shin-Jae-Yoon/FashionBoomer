package com.capstone.capstone.cloth.controller;

import com.capstone.capstone.cloth.entity.Cloth;
import com.capstone.capstone.cloth.mapper.ClothMapper;
import com.capstone.capstone.cloth.service.ClothService;
import com.capstone.capstone.dto.MultiResponseDto;
import com.capstone.capstone.dto.SingleResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v11/clothes")
@Validated
@Slf4j
public class ClothController {
    private final ClothService clothService;
    private final ClothMapper mapper;

    public ClothController(ClothService clothService, ClothMapper mapper) {
        this.clothService = clothService;
        this.mapper = mapper;
    }

    // cloth 정보
    @GetMapping("/{cloth-id}")
    public ResponseEntity getCloth(@PathVariable("cloth-id") @Positive int clothId) throws IOException {
        Cloth cloth = clothService.findCloth(clothId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.clothToClothResponse(cloth)),
        HttpStatus.OK);
    }

    // cloth 이미지
    @GetMapping(value = "/images/{cloth-id}",
            // http 통신으로 이미지 전송 가능하도록 설정
            produces={MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getClothImage(
            @PathVariable("cloth-id") @Positive int clothId) throws IOException {
        Cloth cloth = clothService.findCloth(clothId);

        byte[] image = clothService.pathToImage(cloth.getPath());

        return new ResponseEntity<byte[]>(
                image,
                HttpStatus.OK);
    }

    // clothes 정보
    @GetMapping
    public ResponseEntity getClothes(@Positive @RequestParam int page,
                                      @Positive @RequestParam int size) {
        Page<Cloth> pageClothes = clothService.findClothes(page-1, size);
        List<Cloth> clothes = pageClothes.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.clothesToClothesResponses(clothes),
                        pageClothes),
                HttpStatus.OK);
    }

    // clothes 카테고리 검색
    @GetMapping("/list/{category}/{detail}")
    public ResponseEntity getClothesDetail(@PathVariable("category") @NotBlank String category,
                                           @PathVariable("detail") @NotBlank String detail,
                                            @Positive @RequestParam int page,
                                            @Positive @RequestParam int size) {
        Page<Cloth> clothPage = clothService.findClothesByCategoryAndDetail(category, detail, page-1, size);
        List<Cloth> clothes = clothPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.clothesToClothesResponses(clothes), clothPage),
                HttpStatus.OK);
    }

    // clothes 이미지
    @GetMapping(value = "/images",
            produces={MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public  ResponseEntity<byte[][]> getClothImages(@Positive @RequestParam int page,
                                      @Positive @RequestParam int size) throws IOException {
        byte[][] images = new byte[size][];
        int start = (page-1) * size + 1;

        for(int i = 0; i < size; i++) {
            String path = clothService.findCloth(start + i).getPath();
            images[i] = (clothService.pathToImage(path));
        }

        return new ResponseEntity<>(
                images,
                HttpStatus.OK);
    }
}
