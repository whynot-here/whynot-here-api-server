package handong.whynot.handler;

import handong.whynot.dto.accusation.AccusationResponseCode;
import handong.whynot.dto.common.ErrorResponseDTO;
import handong.whynot.exception.accusation.AccusationNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class AccusationExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(AccusationNotFoundException.class)
    public ErrorResponseDTO accusationNotFoundExceptionHandle() {
        return ErrorResponseDTO.of(AccusationResponseCode.ACCUSATION_READ_FAIL, null);
    }
}
