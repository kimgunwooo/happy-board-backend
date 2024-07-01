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
    ESSENTIAL_FIELD_MISSING_ERROR(NO_CONTENT , "H003","필수적인 필드 부재"),
    INVALID_VALUE_ERROR(NOT_ACCEPTABLE , "H004","값이 유효하지 않음"),
    MEMBER_NOT_FOUND_ERROR(BAD_REQUEST, "H005", "해당 멤버를 찾을 수 없음"),
    COMMENT_NOT_FOUND_ERROR(BAD_REQUEST, "H006", "해당 댓글을 찾을 수 없음"),

    // member
    DUPLICATED_EMAIL(CONFLICT, "M001", "email 중복"),
    DUPLICATED_NICKNAME(CONFLICT, "M002", "nickname 중복"),
    MEMBER_NOT_FOUND(NOT_FOUND, "M003", "해당 회원이 존재하지 않음"),
    PASSWORD_INVALID(BAD_REQUEST, "M004", "비밀번호가 일치하지 않음"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "M005", "권한 없음"),
    UN_AUTHENTICATION(UNAUTHORIZED, "M006", "인증 정보가 존재하지 않음"),

    // file
    FILE_NOT_FOUND(NOT_FOUND, "F001", "파일이 존재하지 않음."),
    FILENAME_NOT_FOUND(NOT_FOUND, "F002", "파일의 이름이 존재하지 않음."),
    FAIL_FILE_UPLOAD(NOT_FOUND, "F003", "파일 업로드에 실패."),
    FILE_TOO_LARGE(PAYLOAD_TOO_LARGE, "F004", "파일 크기가 너무 큼."),

    // board
    BOARD_NOT_FOUND_ERROR(NOT_FOUND, "B001", "해당 게시물을 찾을 수 없음"),
    NOT_BOARD_OWNER(HttpStatus.FORBIDDEN, "B002", "해당 게시물에 대한 권한이 없습니다."),

    ;


    private final HttpStatus status;
    private final String code;
    private final String message;

}
