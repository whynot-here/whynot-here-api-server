package handong.whynot.api.v2;

import handong.whynot.domain.Account;
import handong.whynot.dto.account.AccountResponseDTO;
import handong.whynot.dto.account.SignInRequestDTO;
import handong.whynot.dto.account.TokenResponseDTO;
import handong.whynot.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class AccountControllerV2 {

    private final AccountService accountService;

    @PostMapping("/sign-in")
    public TokenResponseDTO signIn(@RequestBody @Valid SignInRequestDTO signInRequest, HttpServletResponse response) {

        TokenResponseDTO tokenResponseDTO = accountService.signIn(signInRequest);

        Cookie cookie = new Cookie("refresh", tokenResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);    // 자바스크립트로 쿠키를 조회하는 것을 막는 옵션

        response.addCookie(cookie);

        return tokenResponseDTO;
    }

    @Operation(summary = "계정 정보 조회")
    @GetMapping("/account/info")
    public AccountResponseDTO getAccountInfo() {
        Account account = accountService.getCurrentAccount();

        return AccountResponseDTO.builder()
                .id(account.getId())
                .email(account.getEmail())
                .nickname(account.getNickname())
                .profileImg(account.getProfileImg())
                .authType(account.getAuthType())
                .build();
    }
}
