package handong.whynot.service;

import handong.whynot.domain.Account;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.account.SignUpDTO;
import handong.whynot.dto.account.UserAccount;
import handong.whynot.exception.account.AccountAlreadyExistEmailException;
import handong.whynot.exception.account.AccountAlreadyExistNicknameException;
import handong.whynot.exception.account.AccountNotValidToken;
import handong.whynot.mail.EmailMessage;
import handong.whynot.mail.EmailService;
import handong.whynot.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String emailOrNickname) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(emailOrNickname);
        if (account == null) {
            account = accountRepository.findByNickname(emailOrNickname);
        }

        if (account == null) {
            throw new UsernameNotFoundException(emailOrNickname);
        }

        return new UserAccount(account);
    }

    @Transactional
    public Account createAccount(SignUpDTO signUpDTO) {

        // 중복 검증 (이메일)
        if (accountRepository.existsByEmail(signUpDTO.getEmail())) {
            throw new AccountAlreadyExistEmailException(AccountResponseCode.ACCOUNT_ALREADY_EXIST_EMAIL);
        }
        // 중복 검증 (닉네임)
        if (accountRepository.existsByNickname(signUpDTO.getNickname())) {
            throw new AccountAlreadyExistNicknameException(AccountResponseCode.ACCOUNT_ALREADY_EXIST_NICKNAME);
        }

        Account account = Account.builder()
                .email(signUpDTO.getEmail())
                .nickname(signUpDTO.getNickname())
                .password(passwordEncoder.encode(signUpDTO.getPassword()))
                .build();
        Account newAccount = accountRepository.save(account);

        newAccount.generateEmailCheckToken();

        sendSignUpConfirmEmail(newAccount);
        return newAccount;
    }

    public void sendSignUpConfirmEmail(Account newAccount) {
        String message = "http://localhost:9000/check-email-token?token=" + newAccount.getEmailCheckToken() +
                "&email=" + newAccount.getEmail();

        EmailMessage emailMessage = EmailMessage.builder()
                .to(newAccount.getEmail())
                .subject("회원 가입 인증")
                .message(message)
                .build();

        emailService.sendEmail(emailMessage);
    }

    public void checkEmail(String token, String email) {

        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new AccountNotValidToken(AccountResponseCode.ACCOUNT_NOT_VALID_TOKEN);
        }

        if (!account.isValidToken(token)) {
            throw new AccountNotValidToken(AccountResponseCode.ACCOUNT_NOT_VALID_TOKEN);
        }

        account.completeSignUp();
        login(account);
    }

    public void login(Account account) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(account),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
