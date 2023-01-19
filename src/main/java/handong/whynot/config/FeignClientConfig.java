package handong.whynot.config;

import feign.Logger;
import feign.Retryer;
import feign.codec.Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;

@Configuration
public class FeignClientConfig {

  @Bean
  Encoder formEncoder() {
    return new feign.form.FormEncoder();
  }

  @Bean
  Retryer.Default retryer() {
    return new Retryer.Default(TimeUnit.SECONDS.toMillis(1L), TimeUnit.SECONDS.toMillis(3L), 5);
  }

  @Bean
  @Profile("local")
  Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }
}
