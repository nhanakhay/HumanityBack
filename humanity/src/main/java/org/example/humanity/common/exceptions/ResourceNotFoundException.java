package org.example.humanity.common.exceptions;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException{
    private String message;
    private Object activityType;
    private String description;

    public ResourceNotFoundException(String message, Object activityType, String description) {
        super(message);
        this.message = message;
        this.activityType = activityType;
        this.description = description;
    }

    public ResourceNotFoundException(String message){
        super(message);
        this.message = message;
    }


}
