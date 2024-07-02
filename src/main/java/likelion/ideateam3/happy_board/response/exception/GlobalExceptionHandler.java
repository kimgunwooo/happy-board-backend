package likelion.ideateam3.happy_board.response.exception;


import likelion.ideateam3.happy_board.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import static likelion.ideateam3.happy_board.response.ResponseUtil.createFailureResponse;

@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseBody<Void>> businessException(BusinessException e) {
        ExceptionType exceptionType = e.getExceptionType();
        return ResponseEntity.status(exceptionType.getStatus())
                .body(createFailureResponse(exceptionType));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseBody<Void>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String customMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity
                .status(ExceptionType.BINDING_ERROR.getStatus())
                .body(createFailureResponse(ExceptionType.BINDING_ERROR, customMessage));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ResponseEntity<ResponseBody<Void>> handleMaxUploadSizeExceededException(
        MaxUploadSizeExceededException e) {
        log.info("handleMaxUploadSizeExceededException : {}", e.getMessage());

        return ResponseEntity
                .status(ExceptionType.FILE_TOO_LARGE.getStatus())
                .body(createFailureResponse(ExceptionType.FILE_TOO_LARGE));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBody<Void>> exception(Exception e) {
        log.error("Exception Message : {} ", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createFailureResponse(ExceptionType.UNEXPECTED_SERVER_ERROR));
    }
}


