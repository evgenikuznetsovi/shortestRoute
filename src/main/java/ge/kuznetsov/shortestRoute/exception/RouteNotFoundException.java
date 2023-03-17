package ge.kuznetsov.shortestRoute.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RouteNotFoundException extends Exception{

    public RouteNotFoundException() {
    }

    public RouteNotFoundException(String message) {
        super(message);
    }

    public RouteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RouteNotFoundException(Throwable cause) {
        super(cause);
    }

    public RouteNotFoundException(String message
            , Throwable cause
            , boolean enableSuppression
            , boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
