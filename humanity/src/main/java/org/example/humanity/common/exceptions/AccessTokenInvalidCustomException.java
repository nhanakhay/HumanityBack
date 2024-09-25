package org.example.humanity.common.exceptions;

import lombok.Data;

@Data
public class AccessTokenInvalidCustomException extends RuntimeException{
    public AccessTokenInvalidCustomException(String message) {
        super(message);
    }
}
