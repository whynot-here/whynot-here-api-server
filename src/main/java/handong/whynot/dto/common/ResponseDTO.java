package handong.whynot.dto.common;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {

    Integer statusCode;
    String message;

    public static ResponseDTO of(ResponseCode responseCode) {
        return ResponseDTO.builder()
                .statusCode(responseCode.getStatusCode())
                .message(responseCode.getMessage())
                .build();
    }
}
