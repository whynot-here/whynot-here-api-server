package handong.whynot.exception.post;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class PostAlreadyFavoriteOff extends AbstractBaseException {

    public PostAlreadyFavoriteOff(ResponseCode responseCode) {
        super(responseCode);
    }
}
