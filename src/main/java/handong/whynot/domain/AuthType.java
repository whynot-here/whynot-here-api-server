package handong.whynot.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum AuthType {
    local,
    google,
    naver,
    kakao,
    apple,
    admin;

    public static Boolean isValidRegistrationId(String registrationId) {
        return Arrays.stream(values()).anyMatch(value -> StringUtils.equals(value.toString(), registrationId));
    }
}
