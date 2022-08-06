package handong.whynot.exception.account;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class OAuth2ExistEmailException extends AbstractBaseException {

    public OAuth2ExistEmailException(ResponseCode responseCode) {
        super(responseCode);
    }
}
