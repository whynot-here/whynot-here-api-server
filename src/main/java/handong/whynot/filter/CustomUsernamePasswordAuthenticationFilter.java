package handong.whynot.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.account.LoginDTO;
import handong.whynot.exception.account.AccountFormDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

@Slf4j
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        UsernamePasswordAuthenticationToken authenticationToken  = new UsernamePasswordAuthenticationToken("","");

        // JSON 로그인 형식
        if(request.getContentType().equals(MimeTypeUtils.APPLICATION_JSON_VALUE)) {
            try {
                LoginDTO loginDTO = objectMapper.readValue(request.getReader().lines().collect(Collectors.joining()), LoginDTO.class);
                authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
            } catch (Exception e) {
                log.error("[Exception] : JSON 형식이 올바르지 않습니다. {}", e.getMessage());
            }
        }

        // FORM-DATA 로그인 형식
        else {
            throw new AccountFormDataException(AccountResponseCode.ACCOUNT_FORM_DATA_SIGN_IN);
        }

        return this.getAuthenticationManager().authenticate(authenticationToken);
    }
}