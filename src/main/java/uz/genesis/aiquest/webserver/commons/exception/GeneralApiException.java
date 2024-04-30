package uz.genesis.aiquest.webserver.commons.exception;

import lombok.Getter;

import java.util.function.Supplier;


@Getter
public class GeneralApiException extends RuntimeException {
    private String errorCode;

    public GeneralApiException(String message) {
        super(message);
    }

    public GeneralApiException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public GeneralApiException(ErrorCodeMsg errorCodeMsg) {
        super(errorCodeMsg.getMessage());
        this.errorCode = errorCodeMsg.getErrorCode();
    }

    public static Supplier<GeneralApiException> throwEx(String message, String errorCode) {
        return () -> new GeneralApiException(message, errorCode);
    }
}
