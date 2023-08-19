package handong.whynot.exception.accusation;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class AccusationNotFoundException extends AbstractBaseException {
  public AccusationNotFoundException(ResponseCode responseCode) {
    super(responseCode);
  }
}
