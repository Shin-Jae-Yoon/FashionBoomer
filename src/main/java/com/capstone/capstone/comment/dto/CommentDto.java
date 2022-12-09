package com.capstone.capstone.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class CommentDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        @Positive
        private Long user_id;

        @Positive
        private int post_id;

        @NotBlank
        private String comment;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {
        private int id;

        @Positive
        private Long user_id;

        @Positive
        private int post_id;

        @NotBlank
        private String comment;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private int id;
        private Long user_id;
        private int post_id;
        private String comment;
        private String user_name;
        private String created_at;
    }
}
