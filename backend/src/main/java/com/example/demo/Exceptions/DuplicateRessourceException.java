package com.example.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateRessourceException extends RuntimeException{

    public DuplicateRessourceException(String message) {
        super(message);
    }
}
