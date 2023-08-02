package handong.whynot.exception.blind_date;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class BlindDateNotAuthenticatedException extends AbstractBaseException {
  public BlindDateNotAuthenticatedException(ResponseCode responseCode) {
    super(responseCode);
  }
}
