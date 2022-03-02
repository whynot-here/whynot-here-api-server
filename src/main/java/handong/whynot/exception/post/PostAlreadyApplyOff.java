package handong.whynot.exception.post;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

// TODO: 22.02.20. seokjae.lee, Exception 클래스 네이밍 통일
public class PostAlreadyApplyOff extends AbstractBaseException {

    public PostAlreadyApplyOff(ResponseCode responseCode) {
        super(responseCode);
    }
}
