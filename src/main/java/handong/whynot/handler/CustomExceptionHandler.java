package handong.whynot.handler;

import handong.whynot.dto.common.ErrorResponseDTO;
import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * AbstractBaseException 예외 공통 처리
     */
    @ExceptionHandler(AbstractBaseException.class)
    public ErrorResponseDTO notFoundExceptionV2(HttpServletRequest req, AbstractBaseException e) {
        log.error("request url={}", req.getRequestURL().toString());
        ResponseCode responseCode = e.getResponseCode();
        return ErrorResponseDTO.of(responseCode, null);
    }
}
