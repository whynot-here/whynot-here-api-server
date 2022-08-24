package handong.whynot.config;

import handong.whynot.filter.CustomAuthorizationFilter;
import handong.whynot.filter.ExceptionHandlerFilter;
import handong.whynot.handler.security.CustomAuthenticationEntryPoint;
import handong.whynot.handler.security.oauth2.OAuth2FailureHandler;
import handong.whynot.handler.security.oauth2.OAuth2SuccessHandler;
import handong.whynot.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import handong.whynot.service.oauth2.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAuthorizationFilter customAuthorizationFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()   // 개발 단계에서만 허용
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests()
                    // Account
                    .antMatchers("/v1/login", "/v1/sign-up", "/v1/check-email-token", "/v1/resend-token",
                            "/v1/check-email-duplicate", "/v1/check-nickname-duplicate", "/v2/sign-in", "/auth/login/**").permitAll()

                    // Post
                    .antMatchers(HttpMethod.GET,"/v1/posts/**", "/v1/comments/**", "/v2/posts/**", "/v2/posts/category/**").permitAll()
                    .antMatchers("/v1/posts/favorite/**", "/v1/posts/apply/**", "/v1/posts/own/**", "/v2/posts/**").hasRole("USER")

                    // Category
                    .antMatchers(HttpMethod.GET,"/v2/category/default").permitAll()

                    // Swagger
                    .antMatchers(HttpMethod.GET,"/v1/posts/**", "/v1/comments/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .anyRequest().authenticated()

                .and()
                    .addFilterAfter(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling()
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                    .oauth2Login()
                        .authorizationEndpoint()
                        .baseUri("/auth/login")
                        .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
                    .and()
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler(oAuth2FailureHandler)
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService)
        ;

        // 예외처리 필터 등록
        http.addFilterBefore(exceptionHandlerFilter, CustomAuthorizationFilter.class);
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

//        configuration.addAllowedOrigin("https://whynot-here.github.io");
//        configuration.addAllowedOrigin("http://localhost:8080");
//        configuration.addAllowedOrigin("http://localhost:9000");
//        configuration.addAllowedOrigin("https://whynot-here.netlify.app");
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}