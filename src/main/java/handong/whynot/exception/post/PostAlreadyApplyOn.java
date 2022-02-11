package handong.whynot.exception.post;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class PostAlreadyApplyOn extends AbstractBaseException {

    public PostAlreadyApplyOn(ResponseCode responseCode) {
        super(responseCode);
    }
}
