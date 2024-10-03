package com.cammoastay.zzon.reservation.customExceptions;

import org.springframework.http.HttpStatus;

public class PaymentFailedException extends RuntimeException{
    private final HttpStatus status;

    public PaymentFailedException(String message) {
        super(message);
        this.status = HttpStatus.PAYMENT_REQUIRED; // You can customize the HTTP status code here
    }

    public PaymentFailedException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
