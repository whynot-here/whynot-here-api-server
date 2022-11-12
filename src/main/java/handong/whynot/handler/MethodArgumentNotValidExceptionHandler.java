package handong.whynot.handler;

import handong.whynot.dto.admin.AdminResponseCode;
import handong.whynot.dto.common.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO postNotFoundExceptionHandle(Exception e) {
        return ErrorResponseDTO.of(AdminResponseCode.NOT_VALID_CONSTRAINT, null);
    }
}
