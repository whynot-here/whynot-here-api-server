package handong.whynot.dto.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import handong.whynot.domain.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class BlockAccountResponseDTO {
  private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";

  private String name;
  private String profileImg;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime createdDt;

  public static BlockAccountResponseDTO of(Account account, LocalDateTime dateTime) {
    return builder()
      .name(account.getNickname())
      .profileImg(account.getProfileImg())
      .createdDt(dateTime)
      .build();
  }
}
