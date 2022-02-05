package handong.whynot.exception.account;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class AccountNotValidToken extends AbstractBaseException {

    public AccountNotValidToken(ResponseCode responseCode) {
        super(responseCode);
    }
}
