package handong.whynot.exception.post;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class PostAlreadyApplyOff extends AbstractBaseException {

    public PostAlreadyApplyOff(ResponseCode responseCode) {
        super(responseCode);
    }
}
