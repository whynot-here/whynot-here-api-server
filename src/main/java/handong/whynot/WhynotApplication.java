package handong.whynot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WhynotApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhynotApplication.class, args);
    }

}
