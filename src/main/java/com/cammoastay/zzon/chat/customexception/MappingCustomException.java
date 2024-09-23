package com.cammoastay.zzon.chat.customexception;

public class MappingCustomException extends RuntimeException {

    public MappingCustomException(String message) {
        super(message);  // 부모 클래스의 생성자를 호출하여 메시지를 전달
    }

    // 메시지와 예외의 원인을 함께 받는 생성자
    public MappingCustomException(String message, Throwable cause) {
        super(message, cause);  // 부모 클래스의 생성자를 호출하여 메시지와 원인을 전달
    }
}
