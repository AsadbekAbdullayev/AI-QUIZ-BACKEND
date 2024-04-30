package uz.genesis.aiquest.webserver.commons.exception;

import lombok.Getter;


@Getter
public class GeneralClientErrorException extends RuntimeException {
    private String errorCode;

    public GeneralClientErrorException(String message) {
        super(message);
    }

    public GeneralClientErrorException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public GeneralClientErrorException(ErrorCodeMsg errorCodeMsg) {
        super(errorCodeMsg.getMessage());
        this.errorCode = errorCodeMsg.getErrorCode();
    }
}
