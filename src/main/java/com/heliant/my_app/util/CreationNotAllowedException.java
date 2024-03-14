package com.heliant.my_app.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CreationNotAllowedException extends RuntimeException {

    public CreationNotAllowedException() {
        super();
    }

    public CreationNotAllowedException(final String message) {
        super(message);
    }
}
