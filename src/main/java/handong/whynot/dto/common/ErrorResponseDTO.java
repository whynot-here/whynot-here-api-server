package handong.whynot.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)   // null이면 Json반환 시 제외. (errors가 null인 경우를 위함.)
public class ErrorResponseDTO {

    Integer statusCode;
    String message;
    List<String> errors;

    public static ErrorResponseDTO of(ResponseCode responseCode, List<String> errors) {
        return ErrorResponseDTO.builder()
                .message(responseCode.getMessage())
                .statusCode(responseCode.getStatusCode())
                .errors(errors)
                .build();
    }
}