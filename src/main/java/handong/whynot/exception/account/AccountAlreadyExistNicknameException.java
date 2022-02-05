package handong.whynot.exception.account;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class AccountAlreadyExistNicknameException extends AbstractBaseException {

    public AccountAlreadyExistNicknameException(ResponseCode responseCode) {
        super(responseCode);
    }
}
