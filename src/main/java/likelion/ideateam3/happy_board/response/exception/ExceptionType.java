package likelion.ideateam3.happy_board.response.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ExceptionType {
    // common
    UNEXPECTED_SERVER_ERROR(INTERNAL_SERVER_ERROR, "H001", "예상치못한 서버에러 발생"),
    BINDING_ERROR(BAD_REQUEST, "H002", "바인딩시 에러 발생"),

    // member
    DUPLICATED_EMAIL(CONFLICT, "M001", "email 중복"),
    DUPLICATED_NICKNAME(CONFLICT, "M002", "nickname 중복"),
    MEMBER_NOT_FOUND(NOT_FOUND, "M003", "해당 회원이 존재하지 않음"),
    PASSWORD_INVALID(BAD_REQUEST, "M004", "비밀번호가 일치하지 않음"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "M005", "권한 없음"),
    UN_AUTHENTICATION(UNAUTHORIZED, "M006", "인증 정보가 존재하지 않음")
    ;


    private final HttpStatus status;
    private final String code;
    private final String message;

}
