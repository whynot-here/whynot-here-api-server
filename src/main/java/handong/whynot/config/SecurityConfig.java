package handong.whynot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()   // 개발 단계에서만 허용
                .authorizeRequests()
                .antMatchers("/", "/login", "/sign-up", "/check-email-token",
                        "/email-login", "/login-by-email").permitAll()
                .antMatchers("/v1/posts/favorite/**", "v1/posts/apply/**").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/v1/posts/**").permitAll()
                .anyRequest().authenticated()
        .and()
                .formLogin()
//                .loginPage()  // 프론트 URL 지정 필요
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/v1/posts")
                .permitAll()
        .and()
                .logout()
//                .logoutUrl() // 프론트 URL 지정 필요
                .logoutSuccessUrl("/v1/posts")
        ;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
