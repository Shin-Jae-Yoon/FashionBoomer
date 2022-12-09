package com.capstone.capstone.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class PostDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        @NotBlank
        private String post_title;

        @NotBlank
        private String post_content;

        @Positive
        private int user_id;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {
        private int id;

        @NotBlank
        private String post_title;

        @NotBlank
        private String post_content;

        @Positive
        private int user_id;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private int id;

        private int parentId;

        @NotBlank
        private String post_title;

        @NotBlank
        private String post_content;

        @Positive
        private int user_id;

        private int post_view;

        private int post_like_count;

        private int post_dislike_count;

        private int post_answer_count;

        private int post_comment_count;

        private String user_name;

        private String created_at;

        private String last_modified_at;
    }
}
