package handong.whynot.config;

import feign.codec.Encoder;
import org.springframework.context.annotation.Bean;

public class FeignClientConfig {

  @Bean
  Encoder formEncoder() {
    return new feign.form.FormEncoder();
  }
}
