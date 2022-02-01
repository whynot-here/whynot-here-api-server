package handong.whynot.handler;

import handong.whynot.dto.common.ErrorResponseDTO;
import handong.whynot.dto.job.JobResponseCode;
import handong.whynot.exception.job.JobNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class JobExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(JobNotFoundException.class)
    public ErrorResponseDTO postNotFoundExceptionHandle() {
        return ErrorResponseDTO.of(JobResponseCode.JOB_READ_FAIL, null);
    }
}
