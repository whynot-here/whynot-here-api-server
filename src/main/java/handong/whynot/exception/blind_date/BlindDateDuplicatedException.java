package handong.whynot.exception.blind_date;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class BlindDateDuplicatedException extends AbstractBaseException {
  public BlindDateDuplicatedException(ResponseCode responseCode) {
    super(responseCode);
  }
}
