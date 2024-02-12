package com.demo.bookstore.exception;

public class ResourceNotFoundException extends RuntimeException{
    private int id;

    public ResourceNotFoundException(int id){
        super("Product doesn't exists with productId :"+id);
    }
}
