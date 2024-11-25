package com.resolveai.stations.entities.exceptions;

import java.io.Serial;

public class EmailException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EmailException(String message) {
        super(message);
    }

}