package handong.whynot.exception.account;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class FailToCreateAppleOauth2ClientSecretException extends AbstractBaseException {

  public FailToCreateAppleOauth2ClientSecretException(ResponseCode responseCode) {
    super(responseCode);
  }
}
