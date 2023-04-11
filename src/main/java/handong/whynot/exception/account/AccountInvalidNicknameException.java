package handong.whynot.exception.account;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class AccountInvalidNicknameException extends AbstractBaseException {

    public AccountInvalidNicknameException(ResponseCode responseCode) {
        super(responseCode);
    }
}
