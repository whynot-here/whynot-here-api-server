package handong.whynot.exception.account;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class AccountFormDataException extends AbstractBaseException {

    public AccountFormDataException(ResponseCode responseCode) {
        super(responseCode);
    }
}