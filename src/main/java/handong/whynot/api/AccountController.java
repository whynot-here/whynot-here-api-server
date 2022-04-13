package handong.whynot.api;

import javax.validation.Valid;

import handong.whynot.dto.account.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import handong.whynot.domain.Account;
import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.service.AccountService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/sign-up")
    public ResponseDTO signUp(@Valid @RequestBody SignUpDTO signUpDTO) {

        // Account 저장
        Account account = accountService.createAccount(signUpDTO);

        return ResponseDTO.of(AccountResponseCode.ACCOUNT_CREATE_OK);
    }

    @PostMapping("/check-email-token")
    public ResponseDTO checkEmailToken(@Valid @RequestBody TokenCheckRequestDTO requestDTO) {

        accountService.checkEmail(requestDTO.getToken(), requestDTO.getEmail());

        return ResponseDTO.of(AccountResponseCode.ACCOUNT_VERIFY_OK);
    }

    @PostMapping("/check-email-duplicate")
    public ResponseDTO checkEmailDuplicate(@RequestBody EmailDTO dto) {

        accountService.checkEmailDuplicateAndGenerateAccountAndSendEmail(dto.getEmail());

        return ResponseDTO.of(AccountResponseCode.ACCOUNT_CREATE_TOKEN_OK);
    }

    @PostMapping("/check-nickname-duplicate")
    public ResponseDTO checkNicknameDuplicate(@RequestBody NicknameDTO dto) {

        accountService.checkNicknameDuplicate(dto.getNickname());

        return ResponseDTO.of(AccountResponseCode.ACCOUNT_VALID_DUPLICATE);
    }
}
