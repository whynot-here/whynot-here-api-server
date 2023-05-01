package handong.whynot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Value("${admin.url}")
  private String adminUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000",
                        "http://localhost:3001",
                        "http://localhost:9000",
                        "http://localhost:5173",
                        "https://dev.why-not-here.o-r.kr",
                        "https://why-not-here.o-r.kr",
                        "https://appleid.apple.com",
                        adminUrl
                )
                .allowCredentials(true)
                .allowedMethods("*")
                .exposedHeaders("*");
    }
}