package handong.whynot.exception.account;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class AccountTokenExpiredException extends AbstractBaseException {

    public AccountTokenExpiredException(ResponseCode responseCode) {
        super(responseCode);
    }
}
