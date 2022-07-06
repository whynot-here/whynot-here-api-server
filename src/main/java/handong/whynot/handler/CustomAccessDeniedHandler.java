package handong.whynot.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.common.ErrorResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.apache.commons.codec.CharEncoding.UTF_8;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {

        final ErrorResponseDTO responseMessage = ErrorResponseDTO.of(
                AccountResponseCode.ACCOUNT_FORBIDDEN, List.of("API 접근에 실패하였습니다. 권한을 확인해주세요")
        );

        response.setCharacterEncoding(UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.setStatus(BAD_REQUEST.value());

        try {
            response.getWriter().write(objectMapper.writeValueAsString(responseMessage));
        } catch (IOException e) {
            response.setStatus(INTERNAL_SERVER_ERROR.value());
            log.error("[ExceptionHandlerFilter] Json 생성에 실패하였습니다. {}", e.getMessage());
        }
    }
}
