package handong.whynot.dto.cloud;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class S3ResponseDTO {

    private String url;
    private long size;
}
