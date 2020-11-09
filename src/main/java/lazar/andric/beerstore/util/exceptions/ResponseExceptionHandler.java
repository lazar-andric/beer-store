package lazar.andric.beerstore.util.exceptions;

import lazar.andric.beerstore.util.http.ApiError;
import lazar.andric.beerstore.util.http.ResponseEntityBuilder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(Exception exception) {
        List<String> errors = Collections.singletonList(exception.getMessage());
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Entity not found", errors);
        return ResponseEntityBuilder.build(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException violationException) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Validation error");
        apiError.addValidationErrors(violationException.getConstraintViolations());
        return ResponseEntityBuilder.build(apiError);
    }

    @ExceptionHandler(RestTemplateException.class)
    public ResponseEntity<Object> handleRestTemplateException(RestTemplateException restTemplateException) {
        List<String> errors = Collections.singletonList(restTemplateException.getMessage());
        ApiError apiError;
        if (restTemplateException.getHttpStatus() != null) {
            apiError = new ApiError(restTemplateException.getHttpStatus(), "Too many request", errors);
        } else {
            apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", errors);
        }
        return ResponseEntityBuilder.build(apiError);
    }
}
