package handong.whynot.service;

import handong.whynot.domain.Account;
import handong.whynot.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MobilePushService {

  private final ApplicationEventPublisher eventPublisher;

  public void pushComment(List<Account> accountList, Long postId, String postTitle, String comment) {
    NotificationEvent event = NotificationEvent.builder()
      .accountList(accountList)
      .url(String.format("gather/posts/%d", postId))
      .title(String.format("내 글 [%s] 에 댓글📝이 달렸어요.", postTitle))
      .body(comment)
      .build();

    eventPublisher.publishEvent(event);
  }

  public void pushFavorite(List<Account> accountList, Long postId, String postTitle) {
    NotificationEvent event = NotificationEvent.builder()
      .accountList(accountList)
      .url(String.format("gather/posts/%d", postId))
      .title(("내 게시글에 좋아요💕 를 눌렀습니다."))
      .body(String.format("[%s] (why not here 알림)", postTitle))
      .build();

    eventPublisher.publishEvent(event);
  }

  public void pushApproveStudentAuth(List<Account> accountList) {
    NotificationEvent event = NotificationEvent.builder()
      .accountList(accountList)
      .url("auth")
      .title(("학생증 인증이 완료되었습니다. 🎁"))
      .body("(why not here 알림)")
      .build();

    eventPublisher.publishEvent(event);
  }
}