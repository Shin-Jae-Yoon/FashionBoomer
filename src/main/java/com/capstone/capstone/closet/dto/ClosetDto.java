package com.capstone.capstone.closet.dto;

import com.capstone.capstone.validator.NotSpace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ClosetDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        @NotBlank
        private Long user_id;

        @NotBlank
        private Long cloth_id;
    }

    @AllArgsConstructor
    @Getter
    public static class Response {
        private int id;
        private Long user_id;
        private int cloth_id;
    }
}
