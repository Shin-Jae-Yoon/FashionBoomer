package com.capstone.capstone.member.controller;

import com.capstone.capstone.dto.MultiResponseDto;
import com.capstone.capstone.dto.SingleResponseDto;
import com.capstone.capstone.member.entity.Member;
import com.capstone.capstone.member.dto.MemberDto;
import com.capstone.capstone.member.mapper.MemberMapper;
import com.capstone.capstone.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

//@Controller
//@RequestMapping("/v11/members")
//public class MemberController {
//    private final MemberService memberService;
//    private final MemberMapper mapper;
//
//    public MemberController(MemberService memberService, MemberMapper mapper) {
//        this.memberService = memberService;
//        this.mapper = mapper;
//    }
//
//    @PostMapping
//    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody) {
//        Member member = mapper.memberPostToMember(requestBody);
//
//        Member createdMember = memberService.createMember(member);
//        MemberDto.Response response = mapper.memberToMemberResponse(createdMember);
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(response),
//                HttpStatus.CREATED);
//    }
//
//    @PatchMapping("/{member-id}")
//    public ResponseEntity patchMember(
//            @PathVariable("member-id") @Positive long memberId,
//            @Valid @RequestBody MemberDto.Patch requestBody) {
//        requestBody.setMemberId(memberId);
//
//        Member member =
//                memberService.updateMember(mapper.memberPatchToMember(requestBody));
//
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(mapper.memberToMemberResponse(member)),
//                HttpStatus.OK);
//    }
//
//    @GetMapping("/{member-id}")
//    public ResponseEntity getMember(
//            @PathVariable("member-id") @Positive long memberId) {
//        Member member = memberService.findMember(memberId);
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(mapper.memberToMemberResponse(member))
//                , HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity getMembers(@Positive @RequestParam int page,
//                                     @Positive @RequestParam int size) {
//        Page<Member> pageMembers = memberService.findMembers(page - 1, size);
//        List<Member> members = pageMembers.getContent();
//        return new ResponseEntity<>(
//                new MultiResponseDto<>(mapper.membersToMemberResponses(members),
//                        pageMembers),
//                HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{member-id}")
//    public ResponseEntity deleteMember(
//            @PathVariable("member-id") @Positive long memberId) {
//        memberService.deleteMember(memberId);
//
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @GetMapping("/register")
//    public String registerMemberForm() {
//        return "member-register";
//    }
//
//    @PostMapping("/register")
//    public String registerMember(@Valid MemberDto.Post requestBody) {
//        Member member = mapper.memberPostToMember(requestBody);
//        memberService.createMember(member);
//
//        System.out.println("Member Registration Successfully");
//        return "login";
//    }
//
//    @GetMapping("/my-page")
//    public String myPage() {
//        return "my-page";
//    }
//}

@RestController
@RequestMapping("/v11/members")
@Validated
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody) {
        Member member = mapper.memberPostToMember(requestBody);
        Member savedMember = memberService.createMember(member);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.memberToMemberResponse(savedMember)), HttpStatus.CREATED
        );
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(
            @PathVariable("member-id") @Positive long memberId,
            @Valid @RequestBody MemberDto.Patch requestBody) {
        requestBody.setMemberId(memberId);
        Member member = memberService.updateMember(mapper.memberPatchToMember(requestBody));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.memberToMemberResponse(member)), HttpStatus.OK
        );
    }
}