package handong.whynot.exception.friend_meeting;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class FriendMeetingNotAuthenticatedException extends AbstractBaseException {
  public FriendMeetingNotAuthenticatedException(ResponseCode responseCode) {
    super(responseCode);
  }
}
