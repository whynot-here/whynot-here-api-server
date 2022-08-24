package handong.whynot.util;

import org.apache.commons.lang3.RandomUtils;

import java.util.List;

public final class NicknameMaker {
    private static final List<String> PREFIX_LIST = List.of("오늘학식메뉴좀", "버거킹은쿠폰으로만", "야식은구름다리",
            "족막은진리", "자체공강", "휴학각", "산책하고싶은사람", "투머치토커", "복학생", "양덕사는사람", "햄최십", "장학생", "제적당할뻔한",
            "뒷자리선호", "앞자리선호", "우주공강", "화석", "대학원생", "우주스타", "혼밥하는", "이번생은", "오늘은", "8학기내내",
            "점심밥고는", "푸른바다", "여기모여라", "내일중간고사", "새내기", "행복한", "아이스아메리카노", "방귀쟁이", "촛불", "휴강기원1일차",
            "작심삼일", "과탑", "자린고비", "아기고양이", "농사꾼", "내 꿈은", "비타민");

    private NicknameMaker() {}

    public static String make(String nickname) {
        return PREFIX_LIST.get(RandomUtils.nextInt(0, PREFIX_LIST.size() - 1)) + nickname;
    }
}