package com.heliant.my_app.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.CONFLICT)
public class ReferencedException extends RuntimeException {

    public ReferencedException() {
        super();
    }

    public ReferencedException(final ReferencedWarning referencedWarning) {
        super(String.format("The operation cannot be completed due to existing relationships with other entities.: %s",referencedWarning.toMessage()));
    }

}
