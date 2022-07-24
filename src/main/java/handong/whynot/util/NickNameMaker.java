package handong.whynot.util;

import java.util.List;

import org.apache.commons.lang3.RandomUtils;

public final class NickNameMaker {

    private NickNameMaker() {}

    private static final List<String> PREFIX_LIST = List.of("귀여운", "시무룩한", "정신없는");

    public static String make(String nickname) {
        return PREFIX_LIST.get(RandomUtils.nextInt(0, PREFIX_LIST.size() - 1)) + nickname;
    }
}
