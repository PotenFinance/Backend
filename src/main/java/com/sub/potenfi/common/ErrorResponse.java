package com.sub.potenfi.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String message;  // 오류 메시지

    public ErrorResponse(String message) {
        this.message = message;
    }
}
