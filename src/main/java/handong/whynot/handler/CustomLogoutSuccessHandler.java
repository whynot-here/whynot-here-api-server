package handong.whynot.handler;

import static org.apache.commons.codec.CharEncoding.UTF_8;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {

        ResponseDTO responseMessage = ResponseDTO.of(AccountResponseCode.ACCOUNT_LOGOUT_SUCCESS);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8);

        response.setStatus(OK.value());

        try {
            response.getWriter().write(objectMapper.writeValueAsString(responseMessage));
        } catch (IOException e) {
            response.setStatus(INTERNAL_SERVER_ERROR.value());
            log.error("[ExceptionHandlerFilter] Json 생성에 실패하였습니다. {}", e.getMessage());
        }
    }
}