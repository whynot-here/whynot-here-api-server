package handong.whynot.exception.account;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class AccountAlreadyExistRoleException extends AbstractBaseException {

  public AccountAlreadyExistRoleException(ResponseCode responseCode) {
    super(responseCode);
  }
}
