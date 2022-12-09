package com.capstone.capstone.vote.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;

public class VoteDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        @Positive
        private int post_id;

        @Positive
        private long member_id;

        private int vote_type;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private int id;

        @Positive
        private int post_id;

        @Positive
        private long member_id;

        private int vote_type;
    }
}
