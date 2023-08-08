package handong.whynot.handler;

import handong.whynot.dto.blind_date.BlindDateResponseCode;
import handong.whynot.dto.common.ErrorResponseDTO;
import handong.whynot.exception.blind_date.BlindDateDuplicatedException;
import handong.whynot.exception.blind_date.BlindDateNotAuthenticatedException;
import handong.whynot.exception.blind_date.BlindDateNotFoundException;
import handong.whynot.exception.blind_date.BlindDateNotMatchedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class BlindDateExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(BlindDateNotFoundException.class)
    public ErrorResponseDTO blindDateNotFoundExceptionHandle() {
        return ErrorResponseDTO.of(BlindDateResponseCode.BLIND_DATE_READ_FAIL, null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BlindDateNotAuthenticatedException.class)
    public ErrorResponseDTO blindDateNotAuthenticatedExceptionHandle() {
        return ErrorResponseDTO.of(BlindDateResponseCode.BLIND_DATE_NOT_AUTHENTICATED, null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BlindDateDuplicatedException.class)
    public ErrorResponseDTO blindDateDuplicatedExceptionHandle() {
        return ErrorResponseDTO.of(BlindDateResponseCode.BLIND_DATE_DUPLICATED, null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BlindDateNotMatchedException.class)
    public ErrorResponseDTO blindDateNotMatchedExceptionHandle() {
        return ErrorResponseDTO.of(BlindDateResponseCode.BLIND_DATE_NOT_MATCHED, null);
    }
}
