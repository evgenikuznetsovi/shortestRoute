package ge.kuznetsov.shortestRoute.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PlanetNotFoundException extends Exception {

    public PlanetNotFoundException() {
    }

    public PlanetNotFoundException(String message) {
        super(message);
    }

    public PlanetNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlanetNotFoundException(Throwable cause) {
        super(cause);
    }

    public PlanetNotFoundException(String message
            , Throwable cause
            , boolean enableSuppression
            , boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
