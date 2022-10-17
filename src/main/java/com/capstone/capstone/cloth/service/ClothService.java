package com.capstone.capstone.cloth.service;

import com.capstone.capstone.cloth.entity.Cloth;
import com.capstone.capstone.cloth.repository.ClothRepository;
import com.capstone.capstone.exception.BusinessLogicException;
import com.capstone.capstone.exception.ExceptionCode;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

        InputStream imageStream = new FileInputStream(path);
        System.out.println(path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return imageByteArray;
    }

    @Transactional
    public byte[] pathToImages(String path) throws IOException {

        InputStream imageStream = new FileInputStream(path);
        System.out.println(path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return imageByteArray;
    }
}
