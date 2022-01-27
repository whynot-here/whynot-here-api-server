package handong.whynot.exception.post;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class PostNotFoundException extends AbstractBaseException {

    public PostNotFoundException(ResponseCode responseCode) {
        super(responseCode);
    }
}
