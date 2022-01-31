package handong.whynot.exception;

import handong.whynot.dto.common.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public abstract class AbstractBaseException extends RuntimeException{

    private final ResponseCode responseCode;

}
