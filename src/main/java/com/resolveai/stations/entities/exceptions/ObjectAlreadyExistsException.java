package com.resolveai.stations.entities.exceptions;

import java.io.Serial;

public class ObjectAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ObjectAlreadyExistsException(String message) {
        super(message);
    }

}

