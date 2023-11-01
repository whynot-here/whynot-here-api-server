package handong.whynot.exception.friend_meeting;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class FriendMeetingNotMatchedException extends AbstractBaseException {
  public FriendMeetingNotMatchedException(ResponseCode responseCode) {
    super(responseCode);
  }
}
