package handong.whynot.dto.common;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseDTO {

    private Integer statusCode;
    private String message;

    public static ResponseDTO of(ResponseCode responseCode) {
        return builder()
                .statusCode(responseCode.getStatusCode())
                .message(responseCode.getMessage())
                .build();
    }
}
