package handong.whynot.exception.blind_date;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class BlindDateFeeNotFoundException extends AbstractBaseException {

  public BlindDateFeeNotFoundException(ResponseCode responseCode) {
    super(responseCode);
  }
}
