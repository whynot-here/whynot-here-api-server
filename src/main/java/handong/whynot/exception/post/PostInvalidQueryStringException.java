package handong.whynot.exception.post;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class PostInvalidQueryStringException extends AbstractBaseException {

    public PostInvalidQueryStringException(ResponseCode responseCode) {
        super(responseCode);
    }
}
