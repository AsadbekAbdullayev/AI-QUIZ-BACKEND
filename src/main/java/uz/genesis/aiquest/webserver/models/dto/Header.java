package uz.genesis.aiquest.webserver.models.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;

import java.time.LocalDateTime;

import static uz.genesis.aiquest.webserver.utils.ErrorCodes.ERROR;
import static uz.genesis.aiquest.webserver.utils.ErrorCodes.SUCCESS;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Header<T> {


    private LocalDateTime transactionTime;

    private String resultCode;

    private String resultMsg;

    @Valid
    @NotNull(message = "data " + ErrorMessages.SHOULDNT_BE_NULL)
    private T data;

    private PaginationData pagination;

    public static <T> Header<T> ok() {
        return Header.<T>builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("0000")
                .resultMsg("OK")
                .build();
    }

    public static <T> Header<T> ok(T data) {
        return Header.<T>builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(SUCCESS)
                .resultMsg("OK")
                .data(data)
                .build();
    }

    public static <T> Header<T> ok(T data, PaginationData pagination) {
        return Header.<T>builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(SUCCESS)
                .resultMsg("OK")
                .data(data)
                .pagination(pagination)
                .build();
    }

    public static <T> Header<T> error() {
        return Header.<T>builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(ERROR)
                .resultMsg("ERROR")
                .build();
    }

    public static <T> Header<T> error(String msg) {
        return Header.<T>builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(ERROR)
                .resultMsg(msg)
                .build();
    }

    public static <T> Header<T> error(String customCode,
                                      String msg) {
        return Header.<T>builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(customCode)
                .resultMsg(msg)
                .build();
    }

    public static <T> Header<T> unsupportedOperation() {
        return Header.<T>builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(ERROR)
                .resultMsg("unsupported_operation")
                .build();
    }
}