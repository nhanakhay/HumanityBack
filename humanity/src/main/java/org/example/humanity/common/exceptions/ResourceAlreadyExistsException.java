package org.example.humanity.common.exceptions;

import lombok.Data;

@Data
public class ResourceAlreadyExistsException extends RuntimeException {
    private Object activityType;
    private String description;
    private String message;
    public ResourceAlreadyExistsException(String message, Object activityType, String description) {
        super(message);
        this.activityType = activityType;
        this.description = description;
    }

    public ResourceAlreadyExistsException(String message) {
        super(message);
        this.message = message;

    }
}