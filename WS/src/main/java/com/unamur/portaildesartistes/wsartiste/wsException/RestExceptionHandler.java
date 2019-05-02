package com.unamur.portaildesartistes.wsartiste.wsException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new BaseException(HttpStatus.BAD_REQUEST, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(BaseException baseError) {
        return new ResponseEntity<>(baseError, baseError.getStatus());
    }

    //other exception handlers below
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        BaseException baseException = new BaseException(NOT_FOUND);
        baseException.setMessage(ex.getMessage());
        return buildResponseEntity(baseException);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected  ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        BaseException baseException = new BaseException(HttpStatus.FORBIDDEN);
        baseException.setMessage(ex.getMessage());
        return buildResponseEntity(baseException);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        BaseException baseException = new BaseException(HttpStatus.INTERNAL_SERVER_ERROR);
        baseException.setMessage(ex.getMessage());
        return buildResponseEntity(baseException);
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiError object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            WebRequest request) {
        BaseException baseError = new BaseException(BAD_REQUEST);
        baseError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        baseError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(baseError);
    }

}
