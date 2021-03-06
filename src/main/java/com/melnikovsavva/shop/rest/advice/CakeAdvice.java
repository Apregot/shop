package com.melnikovsavva.shop.rest.advice;

import com.melnikovsavva.shop.exception.CakeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CakeAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CakeNotFoundException.class)
    public String cakeNotFound(){
        return "ERROR NOT FOUND";
    }
}
