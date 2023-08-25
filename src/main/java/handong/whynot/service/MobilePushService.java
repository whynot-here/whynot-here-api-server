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

  public void pushAdminAuth(List<Account> accountList, Integer count) {
    NotificationEvent event = NotificationEvent.builder()
      .accountList(accountList)
      .url("")
      .title(("💡 학생증 인증 요청"))
      .body(String.format("관리자님, 승인되지 않은 요청이 %d건 있습니다.", count))
      .build();

    eventPublisher.publishEvent(event);
  }

  public void pushAdminPostAccusation(List<Account> accountList, Long postId) {
    NotificationEvent event = NotificationEvent.builder()
      .accountList(accountList)
      .url(String.format("gather/posts/%d", postId))
      .title(("게시글 신고가 접수되었습니다. 🚨"))
      .body("")
      .build();

    eventPublisher.publishEvent(event);
  }

  public void pushMatchingInfo(List<Account> accountList) {
    NotificationEvent event = NotificationEvent.builder()
      .accountList(accountList)
      .url("blind-date/result")
      .title(("[한대소] 상대방 매칭 완료 🎁"))
      .body("매칭된 상대방의 정보를 확인하고, 응답을 해주세요~!")
      .build();

    eventPublisher.publishEvent(event);
  }
}