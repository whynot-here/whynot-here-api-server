package handong.whynot.exception.friend_meeting;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class FriendMeetingDuplicatedException extends AbstractBaseException {
  public FriendMeetingDuplicatedException(ResponseCode responseCode) {
    super(responseCode);
  }
}
