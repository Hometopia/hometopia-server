package com.hometopia.mediaservice.exception;

import com.hometopia.commons.exception.ProblemDetailsBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ProblemDetail handleGlobalException(Exception e) {
        log.error("Stack trace of Internal Server Error", e);
        return ProblemDetailsBuilder.statusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())
                .type(URI.create("https://problems.hometopia.com/internal-server-error"))
                .title("Internal Server Error")
                .build();
    }
}
