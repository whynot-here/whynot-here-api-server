package handong.whynot.config;

import handong.whynot.handler.CustomAuthenticationEntryPoint;
import handong.whynot.handler.CustomLogoutSuccessHandler;
import handong.whynot.handler.LoginFailureHandler;
import handong.whynot.handler.LoginSuccessHandler;
import handong.whynot.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()   // 개발 단계에서만 허용
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and().exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)
        .and()
                .formLogin()
                .loginProcessingUrl("/v1/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .permitAll()
        .and()
                .logout()
                .logoutUrl("/v1/logout")
                .logoutSuccessHandler(customLogoutSuccessHandler)
        ;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("https://whynot-here.github.io");
        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedOrigin("http://localhost:9000");
        configuration.addAllowedOrigin("https://whynot-here.netlify.app");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}