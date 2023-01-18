package handong.whynot.exception.account;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class FailToReadAppleOauth2PrivateKeyException extends AbstractBaseException {

  public FailToReadAppleOauth2PrivateKeyException(ResponseCode responseCode) {
    super(responseCode);
  }
}
