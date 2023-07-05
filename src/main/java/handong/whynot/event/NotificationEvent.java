package handong.whynot.event;

import handong.whynot.domain.Account;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class NotificationEvent {

  private List<Account> accountList;
  private String title;
  private String body;
  private String url;
}