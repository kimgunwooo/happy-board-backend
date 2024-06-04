package likelion.ideateam3.happy_board.response.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@AllArgsConstructor
public enum ExceptionType {
    // common
    UNEXPECTED_SERVER_ERROR(INTERNAL_SERVER_ERROR, "H001", "예상치못한 서버에러 발생"),
    BINDING_ERROR(BAD_REQUEST, "H002", "바인딩시 에러 발생"),

    ;


    private final HttpStatus status;
    private final String code;
    private final String message;

}
