package handong.whynot.api;

import handong.whynot.domain.Account;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.account.SignUpDTO;
import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.exception.account.AccountNotValidFormException;
import handong.whynot.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/sign-up")
    public ResponseDTO signUp(@Valid @RequestBody SignUpDTO signUpDTO, Errors errors) {
        // TODO: 22.02.20. seokjae.lee, MethodArgumentNotValidException 을 ExceptionHandler로 처리
        // 입력 검증 (사용자)
        if(errors.hasErrors()) {
            throw new AccountNotValidFormException(AccountResponseCode.ACCOUNT_NOT_VALID_FROM);
        }

        // Account 저장
        Account account = accountService.createAccount(signUpDTO);

        return ResponseDTO.of(AccountResponseCode.ACCOUNT_CREATE_TOKEN_OK);
    }

    @GetMapping("/check-email-token")
    public ResponseDTO checkEmailToken(String token, String email) {

        accountService.checkEmail(token, email);

        return ResponseDTO.of(AccountResponseCode.ACCOUNT_VERIFY_OK);
    }
}
