package handong.whynot.api.v2;

import handong.whynot.domain.Account;
import handong.whynot.domain.AuthType;
import handong.whynot.dto.account.*;
import handong.whynot.dto.account.oauth2.AppleServicesResponseDTO;
import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.dto.mobile.DeviceTokenDTO;
import handong.whynot.exception.account.PasswordNotSupportedException;
import handong.whynot.handler.security.oauth2.OAuth2AppleHandler;
import handong.whynot.service.AccountService;
import handong.whynot.service.BlockAccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class AccountControllerV2 {

    private final AccountService accountService;
    private final OAuth2AppleHandler OAuth2AppleHandler;
    private final BlockAccountService blockAccountService;

    final static List<AuthType> localLoginList  = List.of(AuthType.local, AuthType.admin);

    @PostMapping("/sign-in")
    public TokenResponseDTO signIn(@RequestBody @Valid SignInRequestDTO signInRequest, HttpServletResponse response) {

        TokenResponseDTO tokenResponseDTO = accountService.signIn(signInRequest);

        Cookie cookie = new Cookie("refresh", tokenResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);    // 자바스크립트로 쿠키를 조회하는 것을 막는 옵션

        response.addCookie(cookie);

        return tokenResponseDTO;
    }

    @PostMapping("/admin-sign-in")
    public TokenResponseDTO adminSignIn(@RequestBody @Valid SignInRequestDTO signInRequest, HttpServletResponse response) {

        TokenResponseDTO tokenResponseDTO = accountService.adminSignIn(signInRequest);

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

    @Operation(summary = "비밀번호 변경")
    @PutMapping("/account/password")
    public ResponseDTO changeAdminPassword(@RequestBody @Valid PasswordDTO request) {

        Account account = accountService.getCurrentAccount();
        if (! localLoginList.contains(account.getAuthType())) {
            throw new PasswordNotSupportedException(AccountResponseCode.ACCOUNT_PASSWORD_NOT_VALID);
        }

        accountService.updatePassword(account, request.getCurrentPassword(), request.getNewPassword());

        return ResponseDTO.of(AccountResponseCode.ACCOUNT_CHANGE_PASSWORD_OK);
    }

    @Operation(summary = "디바이스 토큰 업데이트")
    @PutMapping("/account/device-token")
    public ResponseDTO updateDeviceToken(@RequestBody DeviceTokenDTO dto) {
        Account account = accountService.getCurrentAccount();
        accountService.updateDeviceToken(account, dto.getToken());

        return ResponseDTO.of(AccountResponseCode.ACCOUNT_UPDATE_DEVICE_TOKEN_OK);
    }

    @Operation(summary = "차단 사용자 조회")
    @GetMapping("/account/block-account")
    public List<BlockAccountResponseDTO> getAllBlockAccount() {
        Account account = accountService.getCurrentAccount();

        return blockAccountService.getAllBlockAccount(account);
    }

    @Operation(summary = "사용자 차단")
    @PostMapping("/account/block-account")
    public ResponseDTO createBlockAccount(@RequestBody BlockAccountRequestDTO request) {
        Account account = accountService.getCurrentAccount();
        blockAccountService.createBlockAccount(account, request);

        return ResponseDTO.of(AccountResponseCode.BLOCK_ACCOUNT_CREATED_OK);
    }

    @Operation(summary = "사용자 차단 해제")
    @DeleteMapping("/account/block-account")
    public ResponseDTO deleteBlockAccount(@RequestBody BlockAccountRequestDTO request) {
        Account account = accountService.getCurrentAccount();
        blockAccountService.deleteBlockAccount(account, request);

        return ResponseDTO.of(AccountResponseCode.BLOCK_ACCOUNT_DELETED_OK);
    }
}
