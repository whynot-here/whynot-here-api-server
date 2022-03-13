package handong.whynot.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.common.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO postNotFoundExceptionHandle() {
        return ErrorResponseDTO.of(AccountResponseCode.ACCOUNT_NOT_VALID_FROM, null);
    }
}
