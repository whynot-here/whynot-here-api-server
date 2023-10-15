package handong.whynot.exception.blind_date;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class BlindDateNotOpenedException extends AbstractBaseException {
  public BlindDateNotOpenedException(ResponseCode responseCode) {
    super(responseCode);
  }
}
