package com.capstone.capstone.cloth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ClothDto {
    @AllArgsConstructor
    @Getter
    public static class Response {
        private int id;
        private String category;
        private String gender;
        private String name;
        private String brand;
        private String price;
        private String link;
        private String path;
        private String path_nukki;
    }
}
