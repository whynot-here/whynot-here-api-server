package handong.whynot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@EnableAsync
@Configuration
public class AsyncConfig {

  @Bean(name = "mobileNotification")
  public Executor threadPoolExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    int processors = Runtime.getRuntime().availableProcessors();

    executor.setThreadNamePrefix("PushAsync-");   // thread 이름 설정
    executor.setCorePoolSize(processors);         // 기본 스레드 수
    executor.setMaxPoolSize(processors*2);        // 최대 스레드 개수
    executor.setQueueCapacity(50);                // 최대 큐 수
    executor.setKeepAliveSeconds(60);             // maxpool size로 인해 덤으로 더 돌아다니는 튜브는 60초 후에 수거해서 정리
    executor.initialize();
    return executor;
  }
}