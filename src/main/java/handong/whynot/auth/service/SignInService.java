package handong.whynot.auth.service;

import handong.whynot.auth.dto.SignInRequestDTO;
import handong.whynot.auth.dto.TokenResponseDTO;
import handong.whynot.domain.Account;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.exception.account.AccountNotFoundException;
import handong.whynot.exception.account.AccountNotValidFormException;
import handong.whynot.repository.AccountRepository;
import handong.whynot.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class SignInService {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final SignInTokenGenerator signInTokenGenerator;

    public TokenResponseDTO signIn(SignInRequestDTO signInRequest) {

        Account account = accountRepository.findByEmail(signInRequest.getEmail());
        if (Objects.isNull(account)) {
            throw new AccountNotFoundException(AccountResponseCode.ACCOUNT_READ_FAIL);
        }

        String accessToken = signInTokenGenerator.accessToken(account.getId());
        String refreshToken = signInTokenGenerator.refreshToken(account.getId());

        return TokenResponseDTO.of(account, accessToken, refreshToken);
    }
}