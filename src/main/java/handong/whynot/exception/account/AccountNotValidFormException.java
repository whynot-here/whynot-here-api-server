package handong.whynot.exception.account;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class AccountNotValidFormException extends AbstractBaseException {

    public AccountNotValidFormException(ResponseCode responseCode) {
        super(responseCode);
    }
}
