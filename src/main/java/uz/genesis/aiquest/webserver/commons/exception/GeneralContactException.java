package uz.genesis.aiquest.webserver.commons.exception;

import lombok.Getter;


@Getter
public class GeneralContactException extends RuntimeException {
    private String errorCode;

    public GeneralContactException(String message) {
        super(message);
    }

    public GeneralContactException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public GeneralContactException(ErrorCodeMsg errorCodeMsg) {
        super(errorCodeMsg.getMessage());
        this.errorCode = errorCodeMsg.getErrorCode();
    }
}
