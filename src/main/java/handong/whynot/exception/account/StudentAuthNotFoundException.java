package handong.whynot.exception.account;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class StudentAuthNotFoundException extends AbstractBaseException {

  public StudentAuthNotFoundException(ResponseCode responseCode) {
    super(responseCode);
  }
}
