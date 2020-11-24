package com.gasparbarancelli.cupons.config;

import com.gasparbarancelli.cupons.exception.CupomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionAdviceConfiguration {

    @ResponseBody
    @ExceptionHandler(CupomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String noResultExceptionHandler(CupomNotFoundException e) {
        return e.getMessage();
    }

}
