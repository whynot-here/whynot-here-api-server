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

  public void pushMatchingSuccess(List<Account> accountList) {
    NotificationEvent event = NotificationEvent.builder()
      .accountList(accountList)
      .url("blind-date/matching")
      .title(("[한대소] 상대방 매칭 완료 🎁"))
      .body("매칭된 상대방의 정보를 확인하고, 연락 해보세요~!")
      .build();

    eventPublisher.publishEvent(event);
  }

  public void pushMatchingFail(List<Account> accountList) {
    NotificationEvent event = NotificationEvent.builder()
      .accountList(accountList)
      .url("blind-date/matching-fail")
      .title(("[한대소] 상대방 매칭 실패 😥"))
      .body("아쉽지만 학우님과 딱 맞는 매칭 상대를 찾지 못했어요 😥")
      .build();

    eventPublisher.publishEvent(event);
  }

  public void pushCustomMessage(List<Account> accountList, String url, String title, String body) {
    NotificationEvent event = NotificationEvent.builder()
      .accountList(accountList)
      .url(url)
      .title(title)
      .body(body)
      .build();

    eventPublisher.publishEvent(event);
  }

  public void pushDeleteBlindDateFee(List<Account> accountList) {
    NotificationEvent event = NotificationEvent.builder()
      .accountList(accountList)
      .url("")   // todo: 프론트 주소 나오면 채우기
      .title(("[한대소] 연애탭 참여 신청 취소 완료"))
      .body("한대소 신청과 보증금 납부 내역이 삭제되었어요~! 다음에 또 만나요!")
      .build();

    eventPublisher.publishEvent(event);
  }

  public void pushApproveBlindDateFee(List<Account> accountList) {
    NotificationEvent event = NotificationEvent.builder()
      .accountList(accountList)
      .url("")   // todo: 프론트 주소 나오면 채우기
      .title(("[한대소] 보증금 납부 확인 완료"))
      .body("신청 정보를 작성하고, 한대소 참여를 완료해주세요~! ☺️")
      .build();

    eventPublisher.publishEvent(event);
  }

  public void pushApproveMatchingImage(List<Account> accountList) {
    NotificationEvent event = NotificationEvent.builder()
      .accountList(accountList)
      .url("")   // todo: 프론트 주소 나오면 채우기
      .title(("[한대소] 만남 인증 완료 ✨"))
      .body("보증금은 시즌 종료 후 순차 환급됩니다 ☺️")
      .build();

    eventPublisher.publishEvent(event);
  }
}