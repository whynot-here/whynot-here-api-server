package handong.whynot.handler;

import handong.whynot.dto.common.ErrorResponseDTO;
import handong.whynot.dto.post.PostResponseCode;
import handong.whynot.exception.post.*;
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
    @ExceptionHandler(PostAlreadyFavoriteOn.class)
    public ErrorResponseDTO postAlreadyFavoriteOn() {
        return ErrorResponseDTO.of(PostResponseCode.POST_CREATE_FAVORITE_FAIL, null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(PostAlreadyFavoriteOff.class)
    public ErrorResponseDTO postAlreadyFavoriteOff() {
        return ErrorResponseDTO.of(PostResponseCode.POST_DELETE_FAVORITE_FAIL, null);
    }
  
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(PostAlreadyApplyOn.class)
    public ErrorResponseDTO postAlreadyApplyOn() {
        return ErrorResponseDTO.of(PostResponseCode.POST_CREATE_APPLY_FAIL, null);
    }
  
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(PostAlreadyApplyOff.class)
    public ErrorResponseDTO postAlreadyApplyOff() {
        return ErrorResponseDTO.of(PostResponseCode.POST_DELETE_APPLY_FAIL, null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(PostInvalidQueryStringException.class)
    public ErrorResponseDTO postInvalidQueryStringHandle() {
        return ErrorResponseDTO.of(PostResponseCode.POST_INVALID_QUERY_STRING, null);
    }
}