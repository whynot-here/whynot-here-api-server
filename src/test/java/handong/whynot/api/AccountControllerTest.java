package handong.whynot.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import handong.whynot.api.v1.AccountController;
import handong.whynot.config.AppConfig;
import handong.whynot.config.SecurityConfig;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.account.SignUpDTO;
import handong.whynot.repository.AccountRepository;
import handong.whynot.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@Import({ SecurityConfig.class, AppConfig.class })
class AccountControllerTest {

    @MockBean
    private AccountService accountService;
    @MockBean
    private AccountRepository accountRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String SIGN_UP_URL = "/sign-up";

    @Test
    void signUpWithValidInput() throws Exception {
        final SignUpDTO signUpDTOWithShortNickName = new SignUpDTO("test",
                                                                   "test@test.com",
                                                                   "12345678");

        mockMvc.perform(post(SIGN_UP_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signUpDTOWithShortNickName)))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(
                   jsonPath("statusCode").value(AccountResponseCode.ACCOUNT_CREATE_TOKEN_OK.getStatusCode()));
    }

    @ParameterizedTest
    @MethodSource("INVALID_NICKNAMES")
    @NullAndEmptySource
    void signUpWithInvalidNickname(String nickname) throws Exception {
        final SignUpDTO signUpDTOWithTooShortNickName = new SignUpDTO(nickname,
                                                                      "test@test.com",
                                                                      "12345678");

        mockMvc.perform(post(SIGN_UP_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signUpDTOWithTooShortNickName)))
               .andDo(print())
               .andExpect(status().is5xxServerError())
               .andExpect(
                   jsonPath("statusCode").value(AccountResponseCode.ACCOUNT_NOT_VALID_FROM.getStatusCode()));
    }

    @ParameterizedTest
    @MethodSource("INVALID_EMAILS")
    @NullAndEmptySource
    void signUpWithInvalidEmail(String email) throws Exception {
        final SignUpDTO signUpDTOWithInvalidEmail = new SignUpDTO("test",
                                                                  email,
                                                                  "12345678");

        mockMvc.perform(post(SIGN_UP_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signUpDTOWithInvalidEmail)))
               .andDo(print())
               .andExpect(status().is5xxServerError())
               .andExpect(
                   jsonPath("statusCode").value(AccountResponseCode.ACCOUNT_NOT_VALID_FROM.getStatusCode()));
    }

    @ParameterizedTest
    @MethodSource("INVALID_PASSWORDS")
    @NullAndEmptySource
    void signUpWithInvalidPassword(String password) throws Exception {
        final SignUpDTO signUpDTOWithInvalidPassword = new SignUpDTO("test",
                                                                     "test@test.com",
                                                                     password);

        mockMvc.perform(post(SIGN_UP_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signUpDTOWithInvalidPassword)))
               .andDo(print())
               .andExpect(status().is5xxServerError())
               .andExpect(
                   jsonPath("statusCode").value(AccountResponseCode.ACCOUNT_NOT_VALID_FROM.getStatusCode()));
    }

    private static List<String> INVALID_NICKNAMES() {
        return List.of("s".repeat(2), "s".repeat(21));
    }

    private static List<String> INVALID_EMAILS() {
        return List.of("invalid-email-pattern", "19dk2390d0e9d");
    }

    private static List<String> INVALID_PASSWORDS() {
        return List.of("1".repeat(7), "1".repeat(51));
    }
}
