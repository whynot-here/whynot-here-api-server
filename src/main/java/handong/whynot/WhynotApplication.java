package handong.whynot;

import handong.whynot.domain.Account;
import handong.whynot.domain.AuthType;
import handong.whynot.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@SpringBootApplication
public class WhynotApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhynotApplication.class, args);
    }

    @Value("${test.user.name}")
    private String username;

    @Value("${test.user.password}")
    private String password;

    @Bean
    CommandLineRunner run(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            try {
                Account account = Account.builder()
                        .id(2L)
                        .nickname(username)
                        .email(username + "@email.com")
                        .password(passwordEncoder.encode(password))
                        .authType(AuthType.local)
                        .build();
                account.completeSignUp();
                accountRepository.save(account);

            } catch (Exception e) {
                log.warn("이미 등록된 아이디입니다.");
            }
        };
    }

}
