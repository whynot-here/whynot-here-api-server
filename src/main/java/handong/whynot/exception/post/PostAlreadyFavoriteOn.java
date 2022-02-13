package handong.whynot.exception.post;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class PostAlreadyFavoriteOn extends AbstractBaseException {

    public PostAlreadyFavoriteOn(ResponseCode responseCode) {
        super(responseCode);
    }
}
