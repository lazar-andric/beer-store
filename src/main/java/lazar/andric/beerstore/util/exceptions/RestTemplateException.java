package lazar.andric.beerstore.util.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RestTemplateException extends RuntimeException{

    private HttpStatus httpStatus;

    public RestTemplateException() {
        super("Something went wrong, please try again later.");
    }

    public RestTemplateException(String message) {
        super(message);
    }

    public RestTemplateException(String message, HttpStatus httpStatus) {
        this(message);
        this.httpStatus = httpStatus;
    }
}
