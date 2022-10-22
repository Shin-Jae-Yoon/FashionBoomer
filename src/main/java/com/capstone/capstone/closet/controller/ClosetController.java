package com.capstone.capstone.closet.controller;

import com.capstone.capstone.closet.dto.ClosetDto;
import com.capstone.capstone.closet.entity.Closet;
import com.capstone.capstone.closet.mapper.ClosetMapper;
import com.capstone.capstone.closet.service.ClosetService;
import com.capstone.capstone.dto.MultiResponseDto;
import com.capstone.capstone.dto.SingleResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v11/closets")
@Validated
@Slf4j
public class ClosetController {
    private final ClosetService closetService;
    private final ClosetMapper mapper;

    public ClosetController(ClosetService closetService, ClosetMapper mapper) {
        this.closetService = closetService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postCloset(@Valid @RequestBody ClosetDto.Post requestBody) {
        // requestBody 데이터를 가지고 옷장(찜) 객체 생성
        Closet closet = mapper.closetPostToCloset(requestBody);

        // 옷장 생성 서비스 처리 후 전송 할 수 있게 데이터 가공
        Closet createdCloset = closetService.createCloset(closet);
        ClosetDto.Response response = mapper.closetToClosetResponse(createdCloset);

        // 클라이언트로 response
        return new ResponseEntity<>(
                new SingleResponseDto<>(response),
                HttpStatus.CREATED);
    }

    // 옷장 id로 검색
    @GetMapping("/{closet-id}")
    public ResponseEntity getCloset(@PathVariable("closet-id") @Positive int closetId) {
        Closet closet = closetService.findCloset(closetId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.closetToClosetResponse(closet)),
        HttpStatus.OK);
    }

    // 옷장 이미지
    @GetMapping(value = "/images/{closet-id}",
            produces={MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getClosetImage(@PathVariable("closet-id") @Positive int closetId) throws IOException {
        Closet closet = closetService.findCloset(closetId);

        byte[] image = closetService.pathToImage(closet.getCloth().getPath());

        return new ResponseEntity<byte[]>(
                image,
                HttpStatus.OK);
    }

    // 옷장 누끼 이미지
    @GetMapping(value = "/images/nukki/{closet-id}",
            produces={MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getClosetNukkiImage(@PathVariable("closet-id") @Positive int closetId) throws IOException {
        Closet closet = closetService.findCloset(closetId);

        byte[] image = closetService.pathToImage(closet.getCloth().getPath_nukki());

        return new ResponseEntity<byte[]>(
                image,
                HttpStatus.OK);
    }


        // 사용자 id로 검색
    @GetMapping("/members/{member-id}")
    public ResponseEntity getMemberClosets(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size,
                                     @PathVariable("member-id") @Positive long memberId) {
        Page<Closet> pageClosets = closetService.findMemberClosets(memberId, page-1, size);
        List<Closet> closets = pageClosets.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.closetsToClosetResponses(closets), pageClosets),
                HttpStatus.OK);
    }

    // 모든 사용자 옷장 전체 조회
    @GetMapping
    public ResponseEntity getClosets(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {
        Page<Closet> pageClosets = closetService.findClosets(page-1, size);
        List<Closet> closets = pageClosets.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.closetsToClosetResponses(closets), pageClosets),
                HttpStatus.OK);
    }

    @DeleteMapping("/{closet-id}")
    public ResponseEntity deleteCloset(@PathVariable("closet-id") @Positive int closetId) {
        closetService.deleteCloset(closetId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
