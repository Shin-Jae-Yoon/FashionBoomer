package com.capstone.capstone.member.dto;

import com.capstone.capstone.member.entity.Member;
import com.capstone.capstone.validator.NotSpace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

public class MemberDto {
//    @Getter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class Post {
//        @NotBlank
//        @Email
//        private String email;
//
//        @NotBlank
//        private String password;
//
//        @NotBlank(message = "이름은 공백이 아니어야 합니다.")
//        private String name;
//
//        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
//                message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
//        private String phone;
//    }
//
//    @Getter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class Patch {
//        private long memberId;
//
//        @NotBlank
//        private String password;
//
//        @NotSpace(message = "회원 이름은 공백이 아니어야 합니다")
//        private String name;
//
//        @NotSpace(message = "휴대폰 번호는 공백이 아니어야 합니다")
//        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
//                message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다")
//        private String phone;
//
//        private Member.MemberStatus memberStatus;
//
//
//        public void setMemberId(long memberId) {
//            this.memberId = memberId;
//        }
//    }
//
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Getter
//    public static class Response {
//        private long memberId;
//        private String email;
//        private String password;
//        private String name;
//        private String phone;
//        private Member.MemberStatus memberStatus;
//
//        public String getMemberStatus() {
//            return memberStatus.getStatus();
//        }
//    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        @Email
        private String email;

        private String name;

        private String platform;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {
        private long memberId;

        @Email
        private String email;

        private String name;

        private String platform;
    }

    @AllArgsConstructor
    @Getter
    public static class Response {
        private long memberId;
        private String email;
        private String name;
        private String platform;
    }
}
