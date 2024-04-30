package uz.genesis.aiquest.webserver.commons.exception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {

    private String errorCode;

    public UnauthorizedException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
