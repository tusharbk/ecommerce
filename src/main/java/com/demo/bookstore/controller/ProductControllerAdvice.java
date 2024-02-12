package com.demo.bookstore.controller;

import com.demo.bookstore.exception.InvalidOptionException;
import com.demo.bookstore.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProductControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    String resourceNotFoundHandler(ResourceNotFoundException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(InvalidOptionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    String invalidOptionHandler(InvalidOptionException ex){
        return ex.getMessage();
    }
}
