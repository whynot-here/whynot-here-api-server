package handong.whynot.config;

import handong.whynot.auth.controller.SignInInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final SignInInterceptor signInInterceptor;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "https://whynot-here.github.io",
                        "http://localhost:8080",
                        "http://localhost:9000",
                        "https://whynot-here.netlify.app"
                )
                .allowedMethods("*")
                .exposedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signInInterceptor)
                .addPathPatterns(signInInterceptor.pathToInclude())
                .excludePathPatterns(signInInterceptor.pathToExclude());
    }
}