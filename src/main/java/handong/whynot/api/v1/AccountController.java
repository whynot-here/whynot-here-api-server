package handong.whynot.api.v1;

import handong.whynot.domain.Account;
import handong.whynot.dto.account.*;
import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "회원 가입")
    @PostMapping("/sign-up")
    public ResponseDTO signUp(@Valid @RequestBody SignUpDTO signUpDTO) {

        // Account 저장
        accountService.createAccount(signUpDTO);

        return ResponseDTO.of(AccountResponseCode.ACCOUNT_CREATE_OK);
    }

    @Operation(summary = "이메일 토큰 인증")
    @PostMapping("/check-email-token")
    public ResponseDTO checkEmailToken(@RequestBody TokenCheckDTO tokenCheckDTO) {

        accountService.checkEmail(tokenCheckDTO.getToken(), tokenCheckDTO.getEmail());

        return ResponseDTO.of(AccountResponseCode.ACCOUNT_VERIFY_OK);
    }

    @Operation(summary = "중복 이메일 체크")
    @PostMapping("/check-email-duplicate")
    public ResponseDTO checkEmailDuplicate(@RequestBody EmailDTO dto) {

        accountService.checkEmailDuplicateAndGenerateAccountAndSendEmail(dto.getEmail());

        return ResponseDTO.of(AccountResponseCode.ACCOUNT_CREATE_TOKEN_OK);
    }

    @Operation(summary = "중복 닉네임 체크")
    @PostMapping("/check-nickname-duplicate")
    public ResponseDTO checkNicknameDuplicate(@RequestBody NicknameDTO dto) {

        accountService.checkNicknameDuplicate(dto.getNickname());

        return ResponseDTO.of(AccountResponseCode.ACCOUNT_VALID_DUPLICATE);
    }

    @Operation(summary = "계정 정보 조회")
    @GetMapping("/account/info")
    public AccountResponseDTO getAccountInfo(@CurrentAccount Account account) {
        return AccountResponseDTO.builder()
                .id(account.getId())
                .email(account.getEmail())
                .nickname(account.getNickname())
                .profileImg(account.getProfileImg())
                .build();
    }

    @Operation(summary = "로그인 상태 정보 조회")
    @GetMapping("/account/login-state")
    public IsLoginDTO getLoginState(@CurrentAccount Account account) {

        if (account != null) {
            return IsLoginDTO.builder()
                    .isLogin(true).build();
        }

        return IsLoginDTO.builder()
                .isLogin(false).build();
    }
}
