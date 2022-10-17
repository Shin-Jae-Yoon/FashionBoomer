package com.capstone.capstone.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    CLOTH_NOT_FOUND(404, "Cloth not found"),
    CLOTH_CODE_EXISTS(409, "Cloth Code exists"),
    CLOSET_NOT_FOUND(404, "Cloth not found"),
    CLOSET_CODE_EXISTS(409, "Cloth Code exists"),
    NOT_IMPLEMENTATION(501, "Not Implementation"),
    INVALID_MEMBER_STATUS(400, "Invalid member status");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}