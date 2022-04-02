package handong.whynot.filter;

import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.common.ErrorResponseDTO;
import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.account.AccountFormDataException;
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

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (AccountFormDataException ex) {
            log.error("[Exception] : form-data 로그인을 지원하지 않습니다.");
            setErrorResponse(ex.getResponseCode(), BAD_REQUEST, response, ex);
        } catch (Exception ex) {
            log.error("Exception: {}", ex.getMessage());
            setErrorResponse(AccountResponseCode.ACCOUNT_FORBIDDEN, BAD_REQUEST, response, ex);
        }
    }

    public void setErrorResponse(ResponseCode responseCode, HttpStatus status,
                                 HttpServletResponse response, Throwable ex) {

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        List<String> errorMessage = new ArrayList<>();
        if(Objects.isNull(ex.getMessage())) {
            errorMessage = null;
        } else {
            errorMessage.add(ex.getMessage());
        }
        ErrorResponseDTO errorResponse = ErrorResponseDTO.of(responseCode, errorMessage);

        try {
            objectMapper.getFactory().configure(JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature(), true);
            String json = objectMapper.writeValueAsString(errorResponse);
            response.getWriter().write(json);
        } catch (IOException e) {
            log.error("[Exception] : JSON 형식이 올바르지 않습니다. {}", e.getMessage());
        }
    }
}