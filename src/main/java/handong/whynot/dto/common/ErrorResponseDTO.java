package handong.whynot.dto.common;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class ErrorResponseDTO {

    private Integer statusCode;
    private String message;
    private List<String> errors;

    public static ErrorResponseDTO of(ResponseCode responseCode, List<String> errors) {
        return builder()
                .message(responseCode.getMessage())
                .statusCode(responseCode.getStatusCode())
                .errors(errors)
                .build();
    }
}