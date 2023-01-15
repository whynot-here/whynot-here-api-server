package handong.whynot.exception.account;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class RoleNotFoundException extends AbstractBaseException {

  public RoleNotFoundException(ResponseCode responseCode) {
    super(responseCode);
  }
}
