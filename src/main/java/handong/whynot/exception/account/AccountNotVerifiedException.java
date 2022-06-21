package handong.whynot.exception.account;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class AccountNotVerifiedException extends AbstractBaseException {
    public AccountNotVerifiedException(ResponseCode responseCode) {
        super(responseCode);
    }
}
