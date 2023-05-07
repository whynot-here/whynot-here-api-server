package handong.whynot.handler;

import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.common.ErrorResponseDTO;
import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.account.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class AccountExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(AccountNotFoundException.class)
    public ErrorResponseDTO postNotFoundExceptionHandle() {
        return ErrorResponseDTO.of(AccountResponseCode.ACCOUNT_READ_FAIL, null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(AccountAlreadyExistEmailException.class)
    public ErrorResponseDTO emailAlreadyExistExceptionHandle() {
        return ErrorResponseDTO.of(AccountResponseCode.ACCOUNT_ALREADY_EXIST_EMAIL, null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(AccountAlreadyExistNicknameException.class)
    public ErrorResponseDTO nicknameAlreadyExistExceptionHandle() {
        return ErrorResponseDTO.of(AccountResponseCode.ACCOUNT_ALREADY_EXIST_NICKNAME, null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(AccountInvalidNicknameException.class)
    public ErrorResponseDTO invalidNicknameExceptionHandle() {
        return ErrorResponseDTO.of(AccountResponseCode.ACCOUNT_INVALID_NICKNAME, null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(AccountNotValidFormException.class)
    public ErrorResponseDTO notValidFormExceptionHandle() {
        return ErrorResponseDTO.of(AccountResponseCode.ACCOUNT_NOT_VALID_FROM, null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(AccountNotValidToken.class)
    public ErrorResponseDTO notValidTokenExceptionHandle(ResponseCode code) {
        return ErrorResponseDTO.of(code, null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(PasswordNotMatchedException.class)
    public ErrorResponseDTO passwordNotMatchedExceptionHandle() {
        return ErrorResponseDTO.of(AccountResponseCode.ACCOUNT_PASSWORD_NOT_MATCHED, null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(PasswordNotSupportedException.class)
    public ErrorResponseDTO passwordNotSupportedExceptionHandle() {
        return ErrorResponseDTO.of(AccountResponseCode.ACCOUNT_PASSWORD_NOT_VALID, null);
    }
}
