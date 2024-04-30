package uz.genesis.aiquest.webserver.commons.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import uz.genesis.aiquest.webserver.models.dto.Header;
import uz.genesis.aiquest.webserver.utils.ErrorCodes;
import uz.genesis.aiquest.webserver.component.Localization;

import java.nio.ByteBuffer;
import java.util.Objects;


@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {

    private final Localization localization;

    @ExceptionHandler(value = {GeneralApiException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public Header<?> handleGeneralApiException(GeneralApiException exception) {
        log.error("Handled Exception {0}", exception);
        return Header.error(exception.getErrorCode(), localization.getMessage(exception.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Header<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        log.error("Handled Exception {0}", ex);
        return Header.error(ErrorCodes.ERR_BAD_REQUEST, Objects.requireNonNull(ex.getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Header<?> handleValidationErrors(MissingServletRequestParameterException ex) {
        log.error("Handled Exception {0}", ex);
        return Header.error(ErrorCodes.ERR_BAD_REQUEST, Objects.requireNonNull(ex.getMessage()));
    }

    @ExceptionHandler({GeneralContactException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public Header<?> handleValidationErrors(GeneralContactException ex) {
        log.error("Handled Exception {0}", ex);
        return Header.error(ex.getErrorCode(), localization.getMessage(ex.getMessage()));
    }

    @ExceptionHandler({GeneralClientErrorException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public Header<?> handleValidationErrors(GeneralClientErrorException ex) {
        log.error("Handled Exception {0}", ex);
        return Header.error(ex.getErrorCode(), ex.getMessage());
    }


    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Header<?> handlerUnauthorizedException(UnauthorizedException ex) {
        log.error("Handled Exception {0}", ex);
        return Header.error(ex.getErrorCode(),localization.getMessage(ex.getMessage()));
    }

    @ExceptionHandler({ UnsupportedMediaTypeStatusException.class})
    @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public Header<?> handlerUnauthorizedException(UnsupportedMediaTypeStatusException ex) {
        log.error("Handled Exception {0}", ex);
        return Header.error(ErrorCodes.ERROR, "Not allowed "+ex.getMessage()+" file type used, please make sure the file exists within list : " + ex.getSupportedMediaTypes());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Header<?> handleValidationErrors(HttpMessageNotReadableException ex) {
        log.error("Handled Exception {0}", ex);
        return Header.error(ErrorCodes.ERR_BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(value = {FeignException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public Header<?> handleFeignException(FeignException feignException) throws JsonProcessingException {
        log.error("Handler exception :", feignException);
        ByteBuffer byteBuffer = feignException.responseBody().orElse(null);
        System.out.println(new String(byteBuffer.array()));
        return Header.error();
    }
}