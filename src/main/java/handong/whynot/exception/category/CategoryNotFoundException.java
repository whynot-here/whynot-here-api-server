package handong.whynot.exception.category;

import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;

public class CategoryNotFoundException extends AbstractBaseException {

    public CategoryNotFoundException(ResponseCode responseCode) {
        super(responseCode);
    }
}
