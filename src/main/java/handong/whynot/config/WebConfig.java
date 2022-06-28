package handong.whynot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
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
}