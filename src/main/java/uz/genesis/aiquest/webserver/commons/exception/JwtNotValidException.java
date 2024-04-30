package uz.genesis.aiquest.webserver.commons.exception;

public class JwtNotValidException extends GeneralApiException {
    public JwtNotValidException(String message) {
        super(message);
    }
}
