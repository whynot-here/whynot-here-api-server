package handong.whynot.event;

import handong.whynot.domain.Account;
import handong.whynot.service.FirebaseService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@Async("mobileNotification")
@Transactional
@RequiredArgsConstructor
public class NotificationEventListener {

  private final FirebaseService firebaseService;

  @EventListener
  public void handleMatchingCompleteEvent(NotificationEvent notificationEvent){

    // 알림 보낼 멤버 목록
    List<Account> accountList = notificationEvent.getAccountList();

    List<String> fcmTokenList = accountList
      .stream()
      .map(Account::getDeviceToken)
      .filter(deviceToken -> !StringUtils.isBlank(deviceToken)).collect(toList());

    if (fcmTokenList.size()!=0){
      firebaseService.sendByTokenList(fcmTokenList,
        notificationEvent.getUrl(),
        notificationEvent.getTitle(),
        notificationEvent.getBody()
      );
    }
  }
}