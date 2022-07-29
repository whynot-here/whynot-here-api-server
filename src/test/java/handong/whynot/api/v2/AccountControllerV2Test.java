package handong.whynot.api.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import handong.whynot.config.AppConfig;
import handong.whynot.config.SecurityConfig;
import handong.whynot.dto.account.AccountResponseDTO;
import handong.whynot.dto.account.SignInRequestDTO;
import handong.whynot.dto.account.TokenResponseDTO;
import handong.whynot.handler.security.CustomAuthenticationEntryPoint;
import handong.whynot.repository.AccountRepository;
import handong.whynot.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountControllerV2.class)
@Import({ SecurityConfig.class, AppConfig.class })
class AccountControllerV2Test {

    @MockBean
    private AccountService accountService;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${test.user.name}")
    private String username;

    @Value("${test.user.password}")
    private String password;

    @Test
    @DisplayName("사용자 로그인 성공 테스트")
    void signInWithValidInput() throws Exception {
        final SignInRequestDTO signInRequestDTO = new SignInRequestDTO(username, password);

        TokenResponseDTO tokenResponseDTO = TokenResponseDTO.builder()
                .accessToken("access-token")
                .refreshToken("refresh-token")
                .accountResponseDTO(AccountResponseDTO.builder().build())
                .build();

        when(accountService.signIn(any())).thenReturn(tokenResponseDTO);

        mockMvc.perform(post("/v2/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequestDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("accessToken").value("access-token"));
    }
}