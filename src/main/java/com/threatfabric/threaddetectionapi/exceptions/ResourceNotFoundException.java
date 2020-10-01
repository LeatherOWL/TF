package com.threatfabric.threaddetectionapi.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message, Object... args) {
        super(String.format(message.replace("{}", "%s"), args));
    }
}
