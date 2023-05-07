package handong.whynot.exception.account;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class PasswordNotMatchedException extends AbstractBaseException {

  public PasswordNotMatchedException(ResponseCode responseCode) {
    super(responseCode);
  }
}
