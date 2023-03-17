package com.yanmakes.lms.v1.exception.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ExceptionDetails {

    @JsonProperty(value = "date-time")
    private LocalDateTime dateTime;

    @JsonProperty(value = "error-message")
    private String message;

    @JsonProperty(value = "details")
    private String details;

    public ExceptionDetails(LocalDateTime dateTime, String message, String details) {
        this.dateTime = dateTime;
        this.message = message;
        this.details = details;
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

}
