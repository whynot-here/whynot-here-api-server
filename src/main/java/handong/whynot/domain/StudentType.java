package handong.whynot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum StudentType {
    ENROLLED("재학생")
    , GRADUATED("졸업생")
    ;

    private final String desc;

    public static Boolean isValidStudentType(String studentType) {
        return Arrays.stream(values()).anyMatch(value -> StringUtils.equals(value.toString(), studentType));
    }
}
