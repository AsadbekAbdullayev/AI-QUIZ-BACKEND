package uz.genesis.aiquest.webserver.utils;


import uz.genesis.aiquest.webserver.commons.exception.ErrorCodeMsg;

import static uz.genesis.aiquest.webserver.utils.ErrorCodes.ERR_NOT_FOUND;

public interface ErrorMessages {

    String NOT_FOUND = "%s not found";

    String SHOULDNT_BE_NULL = "shouldn't be null";
    String SHOULDNT_BE_EMPTY = "shouldn't be empty";
    String ERROR_VALUE = "unprocessable value has been sent";

    String UNPROCESSABLE_ENTITY = "E422";

    static ErrorCodeMsg notFound(String className) {
        return new ErrorCodeMsg(ERR_NOT_FOUND, String.format(NOT_FOUND, className));
    }
}

