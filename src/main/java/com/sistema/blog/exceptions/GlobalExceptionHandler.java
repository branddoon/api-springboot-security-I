package com.sistema.blog.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sistema.blog.dto.ErrorDetails;

/**
 * Centralised exception handler for the entire application.
 * Intercepts exceptions thrown by controllers and translates them into
 * structured {@link ErrorDetails} or field-error map responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // -------------------------------------------------------------------------
    // Exception handlers
    // -------------------------------------------------------------------------

    /**
     * Handles {@link ResourceNotFoundException} and returns a {@code 404 NOT FOUND} response.
     *
     * @param exception  the caught exception
     * @param webRequest the current web request
     * @return a response entity with error details
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException exception, WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link BlogAppException} and returns a {@code 400 BAD REQUEST} response.
     *
     * @param exception  the caught exception
     * @param webRequest the current web request
     * @return a response entity with error details
     */
    @ExceptionHandler(BlogAppException.class)
    public ResponseEntity<ErrorDetails> handleBlogAppException(
            BlogAppException exception, WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Catch-all handler for any unhandled {@link Exception}, returning a
     * {@code 500 INTERNAL SERVER ERROR} response.
     *
     * @param exception  the caught exception
     * @param webRequest the current web request
     * @return a response entity with error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(
            Exception exception, WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles bean-validation failures ({@link MethodArgumentNotValidException}) and
     * returns a map of field names to their respective validation error messages.
     *
     * @param ex      the validation exception
     * @param headers the HTTP headers
     * @param status  the HTTP status
     * @param request the current web request
     * @return a response entity with a field-to-message error map
     */
    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message   = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
