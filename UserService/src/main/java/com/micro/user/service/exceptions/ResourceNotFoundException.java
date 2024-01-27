package com.micro.user.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    //extra properties that you want to manage
    public ResourceNotFoundException(){
        super("Resource not found on server ");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }

}
