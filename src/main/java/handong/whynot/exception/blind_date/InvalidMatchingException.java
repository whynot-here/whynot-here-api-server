package handong.whynot.exception.blind_date;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class InvalidMatchingException extends AbstractBaseException {
  public InvalidMatchingException(ResponseCode responseCode) {
    super(responseCode);
  }
}
