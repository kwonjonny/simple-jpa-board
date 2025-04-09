package jpa.simple.board.board.exception.error_enum;

import jpa.simple.board.exception.error_interface.IErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BoardErrorEnum implements IErrorCode {
    NOT_FOUND_BOARD("B001", "게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
