package gofish.exception;

public class ConfigValidationException extends RuntimeException {

    public ConfigValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigValidationException(String message) {
        super(message);
    }
    
}
