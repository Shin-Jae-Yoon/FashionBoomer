package com.capstone.capstone.closet.service;

import com.capstone.capstone.closet.entity.Closet;
import com.capstone.capstone.closet.repository.ClosetRepository;
import com.capstone.capstone.cloth.service.ClothService;
import com.capstone.capstone.exception.BusinessLogicException;
import com.capstone.capstone.exception.ExceptionCode;
import com.capstone.capstone.member.entity.Member;
import com.capstone.capstone.member.service.MemberService;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ClosetService {
    private final MemberService memberService;
    private final ClothService clothService;
    private final ClosetRepository closetRepository;

    public ClosetService(MemberService memberService, ClothService clothService, ClosetRepository closetRepository) {
        this.memberService = memberService;
        this.clothService = clothService;
        this.closetRepository = closetRepository;
    }

    @Transactional
    public Closet createCloset(Closet closet) {
        long member_id = closet.getMember().getMemberId();
        int cloth_id = closet.getCloth().getId();
        if (!closetRepository.existsById(closet.getId())
                && memberService.findVerifiedMember(member_id) != null
                && clothService.findVerifiedCloth(cloth_id) != null) {
            if (existsByMemberIdAndClothId(member_id, cloth_id)) {
                deleteCloset(closet.getId());
            }
            Closet savedCloset = closetRepository.save(closet);
            return savedCloset;
        }

        return null;
    }

    @Transactional(readOnly = true)
    public Closet findCloset(int id) {
        return findVerifiedCloset(id);
    }

    // 전체 옷장 목록
    @Transactional
    public Page<Closet> findClosets(int page, int size) {
        return closetRepository.findAll(PageRequest.of(page, size,
                Sort.by("Member_MemberId").descending()));
    }

    // 특정 멤버 전체 옷장 목록
    @Transactional
    public Page<Closet> findMemberClosets(long id, int page, int size) {
        List<Closet> closetList = closetRepository.findByMember_MemberId(id);

        Pageable pageable = PageRequest.of(page, size);

        return new PageImpl<>(closetList, pageable, closetList.size());
    }

    @Transactional
    public void deleteCloset(int id) {
        Closet findCloset = findVerifiedCloset(id);

        closetRepository.delete(findCloset);
    }

    @Transactional
    public byte[] pathToImage(String path) throws IOException {

        InputStream imageStream = new FileInputStream(path);
        System.out.println(path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return imageByteArray;
    }

    // find by closet id
    @Transactional(readOnly = true)
    public Closet findVerifiedCloset(int id) {
        Optional<Closet> optionalCloset =
                closetRepository.findById(id);
        Closet findCloset =
                optionalCloset.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.CLOTH_NOT_FOUND));
        return findCloset;
    }

    @Transactional(readOnly = true)
    public boolean existsByMemberIdAndClothId(long member_id, int cloth_id) {
        return closetRepository.findByMember_MemberIdAndCloth_Id(member_id, cloth_id).isPresent();
    }
}
