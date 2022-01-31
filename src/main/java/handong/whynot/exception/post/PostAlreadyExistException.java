package handong.whynot.exception.post;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class PostAlreadyExistException extends AbstractBaseException {

    public PostAlreadyExistException(ResponseCode responseCode) {
        super(responseCode);
    }
}
