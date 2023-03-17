package com.yanmakes.lms.v1.exception.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yanmakes.lms.v1.exception.model.ExceptionDetails;

import java.time.LocalDateTime;

public class ValidationExceptionDetails extends ExceptionDetails {

    @JsonProperty(value = "invalid-field")
    private String field;

    @JsonProperty(value = "total-error-count")
    private int errorCount;

    public ValidationExceptionDetails(LocalDateTime dateTime, String message, String details, int errorCount, String field) {
        super(dateTime, message, details);
        this.errorCount = errorCount;
        this.field = field;
    }

    public int getErrorCount() { return errorCount; }

    public String getField() {
        return field;
    }
}
