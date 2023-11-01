package handong.whynot.exception.friend_meeting;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class FriendMeetingNotFoundException extends AbstractBaseException {
  public FriendMeetingNotFoundException(ResponseCode responseCode) {
    super(responseCode);
  }
}
