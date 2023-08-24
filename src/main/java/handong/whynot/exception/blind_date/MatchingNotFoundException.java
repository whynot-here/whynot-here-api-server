package handong.whynot.exception.blind_date;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class MatchingNotFoundException extends AbstractBaseException {
  public MatchingNotFoundException(ResponseCode responseCode) {
    super(responseCode);
  }
}
