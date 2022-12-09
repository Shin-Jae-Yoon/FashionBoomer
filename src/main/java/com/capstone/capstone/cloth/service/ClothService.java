package com.capstone.capstone.cloth.service;

import com.capstone.capstone.cloth.entity.Cloth;
import com.capstone.capstone.cloth.repository.ClothRepository;
import com.capstone.capstone.exception.BusinessLogicException;
import com.capstone.capstone.exception.ExceptionCode;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ClothService {
    private  final ClothRepository clothRepository;

    public ClothService(ClothRepository clothRepository) {
        this.clothRepository = clothRepository;
    }

    @Transactional(readOnly = true)
    public Cloth findCloth(int id) {
        return  findVerifiedCloth(id);
    }

    @Transactional
    public Page<Cloth> findClothes(int page, int size) {
        return clothRepository.findAll(PageRequest.of(page, size/*,
                Sort.by("id").descending()*/));
    }

    /**
     *  카테고리로 데이터 목록
     */
    @Transactional(readOnly = true)
    public Page<Cloth> findClothesByCategoryAndDetail(String category, String detail, int page, int size) {
        List<Cloth> clothList = clothRepository.findByCategoryAndDetails(category, detail).get();

        Pageable pageable = PageRequest.of(page, size);
        PagedListHolder pagedListHolder =  new PagedListHolder(clothList);
        pagedListHolder.setPageSize(size);
        pagedListHolder.setPage(page);

        return new PageImpl<>(pagedListHolder.getPageList(), pageable, clothList.size());
    }

    /**
     *  카테고리, 디테일, id로 하나의 데이터만
     */
    @Transactional(readOnly = true)
    public Cloth findClothByCategoryAndDetailAndId(String category, String detail, int id) {
        String pathLike = "/" + id + ".";
        Optional<Cloth> optionalCloth =
                clothRepository.findByCategoryAndDetailsAndPathContaining(category, detail, pathLike);
        Cloth findCloth =
                optionalCloth.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.CLOTH_NOT_FOUND));
        return findCloth;
    }

    @Transactional(readOnly = true)
    public Cloth findVerifiedCloth(int id) {
        Optional<Cloth> optionalCloth =
                clothRepository.findById(id);
        Cloth findCloth =
                optionalCloth.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.CLOTH_NOT_FOUND));
        return findCloth;
    }

    @Transactional
    public byte[] pathToImage(String path) throws IOException {
        // path에서 이미지 데이터 load
        InputStream imageStream = new FileInputStream(path);
        // 이미지 데이터를 바이트 배열로 인코딩
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return imageByteArray;
    }

    @Transactional
    public byte[] pathToImages(String path) throws IOException {

        InputStream imageStream = new FileInputStream(path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return imageByteArray;
    }
}
