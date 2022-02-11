package handong.whynot.exception.cloud;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class S3InvalidFileTypeException extends AbstractBaseException {

    public S3InvalidFileTypeException(ResponseCode responseCode) {
        super(responseCode);
    }
}
