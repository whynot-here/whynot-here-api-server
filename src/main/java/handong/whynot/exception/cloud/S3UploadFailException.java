package handong.whynot.exception.cloud;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class S3UploadFailException extends AbstractBaseException {

    public S3UploadFailException(ResponseCode responseCode) {
        super(responseCode);
    }
}
