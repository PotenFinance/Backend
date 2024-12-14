package com.sub.potenfi.common.exception;

// 커스텀 예외 클래스 (NoContentException, 조회 결과 미존재시)
public class NoContentException extends RuntimeException {
    public NoContentException(String message) {
        super(message);
    }
}
