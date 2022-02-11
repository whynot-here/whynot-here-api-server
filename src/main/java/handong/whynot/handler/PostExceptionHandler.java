package handong.whynot.handler;

import handong.whynot.dto.common.ErrorResponseDTO;
import handong.whynot.dto.post.PostResponseCode;
import handong.whynot.exception.post.PostAlreadyApplyOn;
import handong.whynot.exception.post.PostNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class PostExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    public ErrorResponseDTO postNotFoundExceptionHandle() {
        return ErrorResponseDTO.of(PostResponseCode.POST_READ_FAIL, null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(PostAlreadyApplyOn.class)
    public ErrorResponseDTO postAlreadyFavoriteOn() {
        return ErrorResponseDTO.of(PostResponseCode.POST_CREATE_APPLY_FAIL, null);
    }
}
