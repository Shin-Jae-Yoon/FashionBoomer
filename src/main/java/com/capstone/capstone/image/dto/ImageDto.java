package com.capstone.capstone.image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


public class ImageDto {
    @AllArgsConstructor
    @Getter
    public static class Response {
        private byte[] image;
    }
}
