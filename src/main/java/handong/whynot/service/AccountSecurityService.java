package handong.whynot.service;

import handong.whynot.domain.Account;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.account.SignInRequestDTO;
import handong.whynot.dto.account.TokenResponseDTO;
import handong.whynot.exception.account.AccountNotFoundException;
import handong.whynot.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountSecurityService {

    private final AccountRepository accountRepository;
    private final SignInTokenGenerator signInTokenGenerator;
    private final AuthenticationManager authenticationManager;


    public TokenResponseDTO signIn(SignInRequestDTO signInRequest) {

    UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword());

    // SecurityContextHolder 에 저장하는 것은 (UserDetailService -> Provider -> AuthenticationManager 로 전달되는) Authentication 객체
    Authentication authentication = authenticationManager.authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    Account account = accountRepository.findByEmail(signInRequest.getEmail());
    if (Objects.isNull(account)) {
        throw new AccountNotFoundException(AccountResponseCode.ACCOUNT_READ_FAIL);
    }

    String accessToken = signInTokenGenerator.accessToken(account.getId());
    String refreshToken = signInTokenGenerator.refreshToken(account.getId());

    return TokenResponseDTO.of(account, accessToken, refreshToken);
}

    public Account getCurrentAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = accountRepository.findById(Long.valueOf(authentication.getName()))
                .orElseThrow(() -> new AccountNotFoundException(AccountResponseCode.ACCOUNT_READ_FAIL));
        return account;
    }
}
