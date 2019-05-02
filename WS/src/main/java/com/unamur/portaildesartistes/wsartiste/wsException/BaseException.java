
package com.unamur.portaildesartistes.wsartiste.wsException;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class BaseException {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;

    private List<BaseSubException> subErrors;

    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDebugMessage(String message) {
        this.debugMessage = debugMessage;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    private BaseException() {
        timestamp = LocalDateTime.now();
    }

    BaseException(HttpStatus status) {
        this();
        this.status = status;
    }

    BaseException(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.setMessage("Unexpected error");
        this.debugMessage = ex.getLocalizedMessage();
    }

    BaseException(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.setMessage(message);
        this.debugMessage = ex.getLocalizedMessage();
    }

    private void addSubError(BaseSubException subError) {
        if (subErrors == null) {
            subErrors = new ArrayList<>();
        }
        subErrors.add(subError);
    }

    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(new BaseValidationException(object, field, rejectedValue, message));
    }

    private void addValidationError(String object, String message) {
        addSubError(new BaseValidationException(object, message));
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }

    void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    /**
     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
     *
     * @param cv the ConstraintViolation
     */
    private void addValidationError(ConstraintViolation<?> cv) {
        /*
        this.addValidationError(
                cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                cv.getInvalidValue(),
                cv.getMessage());
                */
    }

    void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }

}

