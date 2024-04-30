package uz.genesis.aiquest.webserver.commons.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorCodeMsg {
    private String errorCode;
    private String message;
}
