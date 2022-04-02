package handong.whynot.config;

import handong.whynot.filter.CustomUsernamePasswordAuthenticationFilter;
import handong.whynot.filter.ExceptionHandlerFilter;
import handong.whynot.handler.LoginFailureHandler;
import handong.whynot.handler.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;

    private final ExceptionHandlerFilter exceptionHandlerFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()   // 개발 단계에서만 허용
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests()
                .antMatchers("/", "/v1/login", "/v1/sign-up", "/v1/check-email-token", "/v1/resend-token",
                        "/v1/check-email-duplicate", "/v1/check-nickname-duplicate", "/v1/login").permitAll()
                .antMatchers("/v1/posts/favorite/**", "/v1/posts/apply/**", "/v1/posts/own/**").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/v1/posts/**", "/v1/comments/**","/swagger-ui/**","/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
        .and()
                .formLogin()
                .disable()
        ;

        http.addFilterAt(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, CustomUsernamePasswordAuthenticationFilter.class)
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
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    protected CustomUsernamePasswordAuthenticationFilter getAuthenticationFilter() {
        CustomUsernamePasswordAuthenticationFilter authenticationFilter = new CustomUsernamePasswordAuthenticationFilter();

        try {
            authenticationFilter.setFilterProcessesUrl("/v1/login");
            authenticationFilter.setAuthenticationManager(this.authenticationManagerBean());
            authenticationFilter.setUsernameParameter("email");
            authenticationFilter.setPasswordParameter("password");
            authenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
            authenticationFilter.setAuthenticationFailureHandler(loginFailureHandler);
        } catch (Exception e) {
            log.error("[Exception] : authenticationManagerBean을 생성하지 못했습니다.");
        }

        return authenticationFilter;
    }
}