package uz.genesis.aiquest.webserver.commons.exception;

public class BadRequestException extends GeneralApiException {

    public BadRequestException(String message, String errorCode) {
        super(message, errorCode);
    }
}
