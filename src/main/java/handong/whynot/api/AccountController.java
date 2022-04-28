package handong.whynot.api;

import javax.validation.Valid;

import handong.whynot.dto.account.*;
import org.springframework.web.bind.annotation.*;

import handong.whynot.domain.Account;
import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "회원 가입")
    @PostMapping("/sign-up")
    public ResponseDTO signUp(@Valid @RequestBody SignUpDTO signUpDTO) {

        // Account 저장
        Account account = accountService.createAccount(signUpDTO);

        return ResponseDTO.of(AccountResponseCode.ACCOUNT_CREATE_OK);
    }

    @Operation(summary = "이메일 토큰 인증")
    @PostMapping("/check-email-token")
    public ResponseDTO checkEmailToken(@RequestBody TokenCheckDTO tokenCheckDTO) {

        accountService.checkEmail(tokenCheckDTO.getToken(), tokenCheckDTO.getEmail());

        return ResponseDTO.of(AccountResponseCode.ACCOUNT_VERIFY_OK);
    }

    @Operation(summary = "")
    @PostMapping("/check-email-duplicate")
    public ResponseDTO checkEmailDuplicate(@RequestBody EmailDTO dto) {

        accountService.checkEmailDuplicateAndGenerateAccountAndSendEmail(dto.getEmail());

        return ResponseDTO.of(AccountResponseCode.ACCOUNT_CREATE_TOKEN_OK);
    }

    @Operation(summary = "")
    @PostMapping("/check-nickname-duplicate")
    public ResponseDTO checkNicknameDuplicate(@RequestBody NicknameDTO dto) {

        accountService.checkNicknameDuplicate(dto.getNickname());

        return ResponseDTO.of(AccountResponseCode.ACCOUNT_VALID_DUPLICATE);
    }

    @Operation(summary = "")
    @GetMapping("/account/info")
    public AccountResponseDTO getAccountInfo(@CurrentAccount Account account) {
        return AccountResponseDTO.builder()
                .id(account.getId())
                .email(account.getEmail())
                .nickname(account.getNickname())
                .profileImg(account.getProfileImg())
                .build();
    }

    @Operation(summary = "")
    @GetMapping("/account/login-state")
    public IsLoginDTO getLoginState(@CurrentAccount Account account) {

        if(account != null) {
            return IsLoginDTO.builder()
                    .isLogin(true).build();
        }

        return IsLoginDTO.builder()
                .isLogin(false).build();
    }
}
