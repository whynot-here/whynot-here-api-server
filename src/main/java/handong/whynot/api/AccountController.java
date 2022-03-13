package handong.whynot.api;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import handong.whynot.domain.Account;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.account.SignUpDTO;
import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.service.AccountService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/sign-up")
    public ResponseDTO signUp(@Valid @RequestBody SignUpDTO signUpDTO) {
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
