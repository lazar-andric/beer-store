package lazar.andric.beerstore.util.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override public boolean hasError(ClientHttpResponse response) throws IOException {
        return (
            response.getStatusCode().series() == CLIENT_ERROR
                || response.getStatusCode().series() == SERVER_ERROR);
    }

    @Override public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS)  {
            throw new RestTemplateException("We can't refill beers now, please try again later", HttpStatus.TOO_MANY_REQUESTS);
        }
        throw new RestTemplateException();
    }
}
