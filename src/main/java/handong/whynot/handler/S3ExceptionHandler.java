package handong.whynot.handler;

import handong.whynot.dto.common.ErrorResponseDTO;
import handong.whynot.exception.AbstractBaseException;
import handong.whynot.exception.cloud.S3AclException;
import handong.whynot.exception.cloud.S3InvalidFileTypeException;
import handong.whynot.exception.cloud.S3UploadFailException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class S3ExceptionHandler {

    @ExceptionHandler(S3UploadFailException.class)
    public ErrorResponseDTO s3UploadFailExceptionHandle(HttpServletRequest req, AbstractBaseException e) {
        return ErrorResponseDTO.of(e.getResponseCode(), null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(S3InvalidFileTypeException.class)
    public ErrorResponseDTO s3InvalidFileExceptionHandle(HttpServletRequest req, AbstractBaseException e) {
        return ErrorResponseDTO.of(e.getResponseCode(), null);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(S3AclException.class)
    public ErrorResponseDTO s3AclExceptionHandle(HttpServletRequest req, AbstractBaseException e) {
        return ErrorResponseDTO.of(e.getResponseCode(), null);
    }
}
