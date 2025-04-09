package jpa.simple.board.exception.service_exception;

import jpa.simple.board.exception.error_interface.IErrorCode;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ServiceException extends RuntimeException {

    private final IErrorCode errorCode;

    public ServiceException(final @NotNull IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ServiceException(final String message, final IErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}