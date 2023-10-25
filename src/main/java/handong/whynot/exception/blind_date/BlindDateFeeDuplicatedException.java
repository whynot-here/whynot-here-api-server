package handong.whynot.exception.blind_date;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class BlindDateFeeDuplicatedException extends AbstractBaseException {
  public BlindDateFeeDuplicatedException(ResponseCode responseCode) {
    super(responseCode);
  }
}
