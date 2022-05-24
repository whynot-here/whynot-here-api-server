package handong.whynot.exception.comment;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;


public class CommentNotFoundException extends AbstractBaseException {

    public CommentNotFoundException(ResponseCode responseCode) {
        super(responseCode);
    }
}
