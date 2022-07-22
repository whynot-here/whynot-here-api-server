package handong.whynot.exception.account;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class OAuth2ProcessingException extends AbstractBaseException {

    public OAuth2ProcessingException(ResponseCode responseCode) {
        super(responseCode);
    }
}
