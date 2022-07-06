package handong.whynot.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.common.ErrorResponseDTO;
import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.account.AccountNotVerifiedException;
import handong.whynot.exception.account.AccountTokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.codec.CharEncoding.UTF_8;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (AccountNotVerifiedException ex) {
            log.error("jwt 토큰이 없습니다.");
            setErrorResponse(ex.getResponseCode(), HttpStatus.BAD_REQUEST, response, ex);
        } catch (AccountTokenExpiredException ex) {
            log.error("만료된 토큰입니다.");
            setErrorResponse(ex.getResponseCode(), HttpStatus.BAD_REQUEST, response, ex);
        } catch (Exception ex) {
            log.error("Exception: {}", ex.getMessage());
            setErrorResponse(AccountResponseCode.ACCOUNT_FORBIDDEN, HttpStatus.BAD_REQUEST, response, ex);
        }
    }

    public void setErrorResponse(ResponseCode responseCode, HttpStatus status,
                                 HttpServletResponse response, Throwable ex) {

        List<String> errorMessage = new ArrayList<>();
        if(Objects.isNull(ex.getMessage())) {
            errorMessage = null;
        } else {
            errorMessage.add(ex.getMessage());
        }

        ErrorResponseDTO responseMessage = ErrorResponseDTO.of(responseCode, errorMessage);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8);

        response.setStatus(status.value());

        try {
            response.getWriter().write(objectMapper.writeValueAsString(responseMessage));
        } catch (IOException e) {
            response.setStatus(INTERNAL_SERVER_ERROR.value());
            log.error("[ExceptionHandlerFilter] Json 생성에 실패하였습니다. {}", e.getMessage());
        }
    }
}
