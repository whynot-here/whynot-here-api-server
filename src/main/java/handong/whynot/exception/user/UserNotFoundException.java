package handong.whynot.exception.user;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class UserNotFoundException extends AbstractBaseException {

    public UserNotFoundException(ResponseCode responseCode) {
        super(responseCode);
    }
}
