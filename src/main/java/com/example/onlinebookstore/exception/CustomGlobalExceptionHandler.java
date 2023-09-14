package com.example.onlinebookstore.exception;

import com.example.onlinebookstore.dto.ArgumentNotValidResponse;
import java.time.LocalDateTime;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final int NOT_FOUND_STATUS = 404;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        String[] errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(this::getErrorMessage)
                .toArray(String[]::new);
        return createResponseEntityFromExceptionErrors(errors);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(
            EntityNotFoundException e
    ) {
        return createResponseEntityFromExceptionErrors(new String[]{e.getMessage()});
    }

    private ResponseEntity<Object> createResponseEntityFromExceptionErrors(String[] errors) {
        ArgumentNotValidResponse notValidResponse = new ArgumentNotValidResponse();
        notValidResponse.setTimestamp(LocalDateTime.now());
        notValidResponse.setStatus(HttpStatus.NOT_FOUND);
        notValidResponse.setErrors(errors);
        return new ResponseEntity<>(notValidResponse, HttpStatusCode.valueOf(NOT_FOUND_STATUS));
    }

    @ExceptionHandler(RegistrationException.class)
    protected ResponseEntity<Object> handleRegistrationException(
            RegistrationException e
    ) {
        ArgumentNotValidResponse notValidResponse = new ArgumentNotValidResponse();
        notValidResponse.setTimestamp(LocalDateTime.now());
        notValidResponse.setStatus(HttpStatus.NOT_FOUND);
        notValidResponse.setErrors(new String[]{e.getMessage()});
        return new ResponseEntity<>(notValidResponse, HttpStatusCode.valueOf(NOT_FOUND_STATUS));
    }

    private String getErrorMessage(ObjectError e) {
        if (e instanceof FieldError) {
            String field = ((FieldError) e).getField();
            String message = e.getDefaultMessage();
            return field + " " + message;
        }
        return e.getDefaultMessage();
    }
}
