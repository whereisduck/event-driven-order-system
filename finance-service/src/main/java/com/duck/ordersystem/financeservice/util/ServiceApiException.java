package com.duck.ordersystem.financeservice.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ServiceApiException extends RuntimeException {
    private final HttpStatusCode httpStatusCode;

    public ServiceApiException(HttpStatusCode httpStatus, String message) {
        super(message);
        this.httpStatusCode = httpStatus;
    }
}