package handong.whynot.exception.account;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class BlockAccountExistException extends AbstractBaseException {
  public BlockAccountExistException(ResponseCode responseCode) {
    super(responseCode);
  }
}
