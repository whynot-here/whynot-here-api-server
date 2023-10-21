package handong.whynot.dto.mobile;


import lombok.Getter;

import java.util.List;

@Getter
public class CustomPushRequestDTO {
  private List<Long> accountIds;
  private String url;
  private String title;
  private String body;
}
