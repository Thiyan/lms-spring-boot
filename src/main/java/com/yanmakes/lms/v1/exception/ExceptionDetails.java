package com.yanmakes.lms.v1.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ExceptionDetails {

    @JsonProperty(value = "date-time")
    private LocalDateTime dateTime;

    @JsonProperty(value = "error-message")
    private String message;

    @JsonProperty(value = "invalid-field")
    private String field;

    @JsonProperty(value = "total-error-count")
    private int errorCount;

    @JsonProperty(value = "details")
    private String details;

    public ExceptionDetails(LocalDateTime dateTime, String message, String details) {
        this.dateTime = dateTime;
        this.message = message;
        this.details = details;
    }

    public ExceptionDetails(LocalDateTime dateTime, String message, String details, int errorCount, String field) {
        this.dateTime = dateTime;
        this.message = message;
        this.details = details;
        this.errorCount = errorCount;
        this.field = field;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public int getErrorCount() { return errorCount; }

    public String getField() {
        return field;
    }
}
