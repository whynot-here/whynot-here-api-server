package handong.whynot.service;

import handong.whynot.domain.Account;
import handong.whynot.dto.account.*;
import handong.whynot.dto.post.PostResponseDTO;
import handong.whynot.exception.account.AccountAlreadyExistEmailException;
import handong.whynot.exception.account.AccountAlreadyExistNicknameException;
import handong.whynot.exception.account.AccountNotFoundException;
import handong.whynot.exception.account.AccountNotValidToken;
import handong.whynot.mail.EmailMessage;
import handong.whynot.mail.EmailService;
import handong.whynot.repository.AccountQueryRepository;
import handong.whynot.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AccountQueryRepository accountQueryRepository;
    private final SignInTokenGenerator signInTokenGenerator;
    private final AuthenticationManager authenticationManager;
    private final PostService postService;
    private final CommentService commentService;

    @Override
    public UserDetails loadUserByUsername(String emailOrNickname) {

        Account account = findByEmailOrNickname(emailOrNickname, emailOrNickname);

        if (account == null) {
            throw new AccountNotFoundException(AccountResponseCode.ACCOUNT_READ_FAIL);
        }

        return new UserAccount(account);
    }

    // email인증을 한 사용자를 email 혹은 nickname으로 검색. (없으면 Null 반환)
    private Account findByEmailOrNickname(String email, String nickname) {

        Account account = accountQueryRepository.findByVerifiedEmail(email);

        if (account == null) {
            account = accountQueryRepository.findByVerifiedNickname(nickname);
        }

        return account;
    }

    @Transactional
    public Account createAccount(SignUpDTO signUpDTO) {

        Account account = accountQueryRepository.findByVerifiedEmail(signUpDTO.getEmail());

        if (account == null) {
            throw new AccountNotValidToken(AccountResponseCode.ACCOUNT_NOT_VALID_TOKEN);
        }

        if (account.getPassword() != null) {
            throw new AccountAlreadyExistEmailException(AccountResponseCode.ACCOUNT_ALREADY_EXIST_EMAIL);
        }
        account.setNickname(signUpDTO.getNickname());
        account.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        account.completeSignUp();

        Account savedAccount = accountRepository.save(account);

        login(account);

        return savedAccount;
    }

    public void sendSignUpConfirmEmail(Account newAccount) {

        String message = "인증 코드 8자리 = " + newAccount.getEmailCheckToken();

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
        accountRepository.save(account);
    }

    public void login(Account account) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(account),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @Transactional
    public void checkEmailDuplicateAndGenerateAccountAndSendEmail(String email) {

        Account account = accountQueryRepository.findByVerifiedEmail(email);

        // email인증도 완료된 경우는 중복 처리
        if (account != null) {
            throw new AccountAlreadyExistEmailException(AccountResponseCode.ACCOUNT_ALREADY_EXIST_EMAIL);
        }

        // 동일한 email은 있지만, email 인증이 되지 않은 사용자
        Account savedAccount = accountRepository.findByEmail(email);

        if (savedAccount == null) {

            // 동일한 email도 없는 경우 새로운 사용자
            Account newAccount = Account.builder()
                    .email(email)
                    .emailVerified(false)
                    .build();

            savedAccount = accountRepository.save(newAccount);
        }

        savedAccount.generateEmailCheckToken();
        sendSignUpConfirmEmail(savedAccount);
    }

    public void checkNicknameDuplicate(String nickname) {

        Account account = accountRepository.findByNickname(nickname);

        if (account != null) {
            throw new AccountAlreadyExistNicknameException(AccountResponseCode.ACCOUNT_ALREADY_EXIST_NICKNAME);
        }
    }

    public Account getCurrentAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = accountRepository.findById(Long.valueOf(authentication.getName()))
                .orElseThrow(() -> new AccountNotFoundException(AccountResponseCode.ACCOUNT_READ_FAIL));
        return account;
    }

    @Transactional
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

        String accessToken = signInTokenGenerator.accessToken(account);
        String refreshToken = signInTokenGenerator.refreshToken(account);

        return TokenResponseDTO.of(account, accessToken, refreshToken);
    }

    @Transactional
    public Account updateNickname(String nickname) {

        // 닉네임 중복 검사
        checkNicknameDuplicate(nickname);

        // 닉네임 업데이트
        Account account = getCurrentAccount();
        account.setNickname(nickname);

        return account;
    }

    @Transactional
    public void deleteAccount(Account account) {

        // 1. My 공고 삭제
        postService.getMyPosts(account).forEach(post -> postService.deletePost(post.getId(), account));

        // 2. 북마크 삭제
        postService.getFavorites(account).forEach(post -> postService.deleteFavorite(post.getId(), account));

        // 3. 댓글 삭제
        commentService.deleteByAccount(account);

        accountRepository.delete(account);
    }
}
