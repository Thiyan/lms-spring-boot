package com.yanmakes.lms.v1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT)
public class NoContentException extends RuntimeException {

    public NoContentException(String s) {
        super(s);
    }
}
