package handong.whynot.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FirebaseService {
  @Value("${mobile.push.keyPath}")
  private String keyPath;

  @Value("${mobile.push.scope}")
  private String scope;

  // fcm 기본 설정 진행
  @PostConstruct
  public void init() {
    try {
      FirebaseOptions options = FirebaseOptions.builder()
        .setCredentials(
          GoogleCredentials
            .fromStream(new ClassPathResource(keyPath).getInputStream())
            .createScoped(List.of(scope)))
        .build();
      if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options);
        log.info("Firebase application has been initialized");
      }
    } catch (IOException e) {
      log.error(e.getMessage());
      // spring 뜰때 알림 서버가 잘 동작하지 않는 것이므로 바로 죽임
      throw new RuntimeException(e.getMessage());
    }
  }


  // 알림 보내기
  public void sendByTokenList(List<String> tokenList, String url, String title, String body) {

    // 메시지 만들기
    List<Message> messages = tokenList.stream().map(token -> Message.builder()
      .putData("time", LocalDateTime.now().toString())
      .putData("url", url)
      .setNotification(Notification.builder()
        .setTitle(title)
        .setBody(body)
        .build()
      )
      .setToken(token)
      .build()).collect(Collectors.toList());

    try {
      Thread.sleep(2000);
    } catch (Exception e) {

    }

    // 요청에 대한 응답을 받을 response
    BatchResponse response;
    try {

      // 알림 발송
      response = FirebaseMessaging.getInstance().sendAll(messages);

      // 요청에 대한 응답 처리
      if (response.getFailureCount() > 0) {
        List<SendResponse> responses = response.getResponses();
        List<String> failedTokens = new ArrayList<>();

        for (int i = 0; i < responses.size(); i++) {
          if (!responses.get(i).isSuccessful()) {
            failedTokens.add(tokenList.get(i));
          }
        }
        log.error("List of tokens are not valid FCM token : " + failedTokens);
      }
    } catch (FirebaseMessagingException e) {
      log.error("cannot send to memberList push message. error info : {}", e.getMessage());
    }
  }
}
