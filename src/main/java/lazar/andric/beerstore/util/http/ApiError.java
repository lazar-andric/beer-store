package lazar.andric.beerstore.util.http;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timeStamp;
    private HttpStatus httpStatus;
    private String message;
    private List<String> errors;

    public ApiError() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus httpStatus, String message, List<String> errors) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus httpStatus, String message) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolation) {
        constraintViolation.forEach(this::addValidationError);
    }

    private void addValidationError(ConstraintViolation<?> constraintViolation) {
        errors = new ArrayList<>();
        errors.add(new ApiValidationError(
            ((PathImpl)constraintViolation.getPropertyPath()).getLeafNode().asString(),
            constraintViolation.getMessage()).toString());
    }
}

@AllArgsConstructor
class ApiValidationError {
    private final String field;
    private final String message;

    @Override public String toString() {
        return String.format("%s %s", field, message);
    }
}