package handong.whynot.exception.account;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class AccountNotFoundException extends AbstractBaseException {

    public AccountNotFoundException(ResponseCode responseCode) {
        super(responseCode);
    }
}
