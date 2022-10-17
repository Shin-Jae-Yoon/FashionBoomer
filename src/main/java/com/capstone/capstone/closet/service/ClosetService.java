package com.capstone.capstone.closet.service;

import com.capstone.capstone.closet.entity.Closet;
import com.capstone.capstone.closet.repository.ClosetRepository;
import com.capstone.capstone.exception.BusinessLogicException;
import com.capstone.capstone.exception.ExceptionCode;
import com.capstone.capstone.member.entity.Member;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Transactional
@Service
public class ClosetService {
    private final ClosetRepository closetRepository;

    public ClosetService(ClosetRepository closetRepository) {
        this.closetRepository = closetRepository;
    }

    @Transactional
    public Closet createCloset(Closet closet) {
        if (!closetRepository.existsById(closet.getId())) {
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
                Sort.by("memberId").descending()));
    }

    // 특정 멤버 전체 옷장 목록
    @Transactional
    public Page<Closet> findMemberClosets(long id, int page, int size) {
        return closetRepository.findByMemberId(id, PageRequest.of(page, size,
                Sort.by("memberId").descending()));
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
}
