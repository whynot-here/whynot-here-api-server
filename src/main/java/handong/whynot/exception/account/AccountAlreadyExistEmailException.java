package handong.whynot.exception.account;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class AccountAlreadyExistEmailException extends AbstractBaseException {

    public AccountAlreadyExistEmailException(ResponseCode responseCode) {
        super(responseCode);
    }
}
