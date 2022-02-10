package handong.whynot.exception.cloud;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class S3AclException extends AbstractBaseException {

    public S3AclException(ResponseCode responseCode) {
        super(responseCode);
    }
}
