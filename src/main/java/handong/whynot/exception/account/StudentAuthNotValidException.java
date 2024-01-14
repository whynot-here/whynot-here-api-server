package handong.whynot.exception.account;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class StudentAuthNotValidException extends AbstractBaseException {

  public StudentAuthNotValidException(ResponseCode responseCode) {
    super(responseCode);
  }
}
