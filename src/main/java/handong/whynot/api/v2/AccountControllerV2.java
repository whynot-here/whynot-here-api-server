package handong.whynot.api.v2;

import handong.whynot.domain.Account;
import handong.whynot.dto.account.*;
import handong.whynot.dto.account.oauth2.AppleServicesResponseDTO;
import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.handler.security.oauth2.OAuth2AppleHandler;
import handong.whynot.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class AccountControllerV2 {

    private final AccountService accountService;
    private final OAuth2AppleHandler OAuth2AppleHandler;

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

        return AccountResponseDTO.of(account);
    }

    @Operation(summary = "닉네임 변경")
    @PostMapping("/account/nickname")
    public AccountResponseDTO checkNicknameDuplicate(@RequestBody NicknameDTO dto) {

        Account account = accountService.updateNickname(dto.getNickname());

        return AccountResponseDTO.of(account);
    }

    @PostMapping("/login/apple")
    public void doOAuth2AppleLogin(HttpServletRequest request,
                                   HttpServletResponse response,
                                   AppleServicesResponseDTO serviceResponse) throws Exception {

        try {
            OAuth2AppleHandler.validateRequestCodeAndDoLogin(request, response, serviceResponse);
        } catch (Exception ex) {
            log.error("doOAuth2AppleLogin Apple OAuth 리다이렉션 실패 ", ex);
            throw new Exception("doOAuth2AppleLogin error");
        }
    }

    @DeleteMapping("/account")
    public ResponseDTO deleteAccount() {

        Account account = accountService.getCurrentAccount();
        accountService.deleteAccount(account);
        return ResponseDTO.of(AccountResponseCode.ACCOUNT_DELETE_OK);
    }

    @Operation(summary = "프로필 사진 변경")
    @PutMapping("/account/profileImg")
    public AccountResponseDTO updateProfileImg(@RequestBody ProfileImgDTO dto) {

        Account account = accountService.updateProfileImg(dto);

        return AccountResponseDTO.of(account);
    }
}
