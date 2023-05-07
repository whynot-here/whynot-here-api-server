package handong.whynot.exception.account;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class PasswordNotSupportedException extends AbstractBaseException {

  public PasswordNotSupportedException(ResponseCode responseCode) {
    super(responseCode);
  }
}
