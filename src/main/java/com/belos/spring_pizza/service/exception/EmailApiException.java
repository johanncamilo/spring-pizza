package com.belos.spring_pizza.service.exception;

public class EmailApiException extends RuntimeException {
    public EmailApiException() {
        super ("Error when sending email");
    }
}
