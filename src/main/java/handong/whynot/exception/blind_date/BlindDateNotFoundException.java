package handong.whynot.exception.blind_date;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class BlindDateNotFoundException extends AbstractBaseException {
  public BlindDateNotFoundException(ResponseCode responseCode) {
    super(responseCode);
  }
}
