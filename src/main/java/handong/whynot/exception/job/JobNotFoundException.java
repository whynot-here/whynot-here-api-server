package handong.whynot.exception.job;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class JobNotFoundException extends AbstractBaseException {

    public JobNotFoundException(ResponseCode responseCode) {
        super(responseCode);
    }
}
