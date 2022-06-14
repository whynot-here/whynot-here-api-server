package handong.whynot.handler;

import handong.whynot.dto.comment.CommentResponseCode;
import handong.whynot.dto.common.ErrorResponseDTO;
import handong.whynot.exception.comment.CommentNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class CommentExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(CommentNotFoundException.class)
    public ErrorResponseDTO commentNotFoundExceptionHandle() {
        return ErrorResponseDTO.of(CommentResponseCode.COMMENT_DELETE_FAIL_NOT_FOUND, null);
    }
}
