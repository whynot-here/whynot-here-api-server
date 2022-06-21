package handong.whynot.auth.controller;

import handong.whynot.auth.dto.SignInRequestDTO;
import handong.whynot.auth.dto.TokenResponseDTO;
import handong.whynot.auth.service.SignInService;
import handong.whynot.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final SignInService signInService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/v2/sign-in")
    public TokenResponseDTO signIn(@RequestBody @Valid SignInRequestDTO signInRequest, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword());

        // SecurityContextHolder 에 저장하는 것은 (UserDetailService -> Provider -> AuthenticationManager 로 전달되는) Authentication 객체
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenResponseDTO tokenResponseDTO = signInService.signIn(signInRequest);

        Cookie cookie = new Cookie("refresh", tokenResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);    // 자바스크립트로 쿠키를 조회하는 것을 막는 옵션

        response.addCookie(cookie);

        return tokenResponseDTO;
    }

    @GetMapping("/v2/need/auth")
    public String test1(HttpServletRequest request) {
        Account account = (Account) request.getAttribute("account");
        return account.getEmail();
    }

    @GetMapping("/v2/not/auth")
    public String test2() {
        return "/not/need/auth";
    }
}

