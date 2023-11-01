package handong.whynot.dto.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import handong.whynot.domain.FriendMatchingHistory;
import handong.whynot.domain.MatchingHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class AdminFriendMatchingResponseDTO {

  private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";

  private Long historyId;
  private Integer season;
  private Long friend1;
  private Long friend2;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime createdDt;

  public static AdminFriendMatchingResponseDTO of(FriendMatchingHistory history) {
    return builder()
      .historyId(history.getId())
      .season(history.getSeason())
      .friend1(history.getFriendId1())
      .friend2(history.getFriendId2())
      .createdDt(history.getCreatedDt())
      .build();
  }
}
