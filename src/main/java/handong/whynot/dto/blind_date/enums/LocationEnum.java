package handong.whynot.dto.blind_date.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum LocationEnum {
  DORMITORY("기숙사")
  , POHANG("포항")
  , ETC("그 외 지역")
  , NOT_VALID("입력없음")
  , SEOUL("서울특별시")
  , INCHEON("인천")
  , GYEONGGI_NORTH("경기 북부")
  , GYEONGGI_SOUTH("경기 남부")
  , GANGWON("강원권")
  , CHUNGCHEONG("충청권")
  , DAEJEON("대전")
  , SEJONG("세종")
  , GYEONGSANG("경상권")
  , DAEGU("대구")
  , BUSAN("부산")
  , ULSAN("울산")
  , JEONLA("전라권")
  , GWANGJU("광주")
  , JEJU("제주")
  ;

  private final String desc;

  public static LocationEnum getLocationEnum(String code) {
    return Arrays.stream(LocationEnum.values())
      .filter(a -> a.name().equals(code)).findFirst().orElse(LocationEnum.NOT_VALID);
  }
}
