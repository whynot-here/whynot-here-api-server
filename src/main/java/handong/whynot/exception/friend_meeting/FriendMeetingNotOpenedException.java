package handong.whynot.exception.friend_meeting;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class FriendMeetingNotOpenedException extends AbstractBaseException {
  public FriendMeetingNotOpenedException(ResponseCode responseCode) {
    super(responseCode);
  }
}
