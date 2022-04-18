package com.example.expenseit.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InvalidDataException extends RuntimeException{
    private String message;

    public InvalidDataException(String message){
        super(message);
    }
}
