package handong.whynot.handler.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import handong.whynot.dto.account.AccountResponseDTO;
import handong.whynot.dto.account.UserAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {

        AccountResponseDTO dto = ((UserAccount) authentication.getPrincipal()).getAccount().getAccountDTO();
        writeTokenResponse(response, dto);
    }

    private void writeTokenResponse(HttpServletResponse response, AccountResponseDTO dto) {

        try {
            PrintWriter writer = response.getWriter();
            writer.println(objectMapper.writeValueAsString(dto));
            writer.flush();
        } catch (Exception e) {
            response.setStatus(INTERNAL_SERVER_ERROR.value());
            log.error("Json 응답에 실패했습니다.");
        }
    }
}
