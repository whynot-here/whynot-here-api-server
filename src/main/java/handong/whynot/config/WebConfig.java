package handong.whynot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000",
                        "http://localhost:3001",
                        "http://localhost:9000",
                        "https://dev.why-not-here.o-r.kr",
                        "https://why-not-here.o-r.kr",
                        "http://127.0.0.1:5173",
                        "https://appleid.apple.com"
                )
                .allowCredentials(true)
                .allowedMethods("*")
                .exposedHeaders("*");
    }
}