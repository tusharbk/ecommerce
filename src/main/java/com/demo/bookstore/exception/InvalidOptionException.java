package com.demo.bookstore.exception;

public class InvalidOptionException extends RuntimeException{

    public InvalidOptionException(){
        super("Provide a valid option : discount or tax");
    }
}
