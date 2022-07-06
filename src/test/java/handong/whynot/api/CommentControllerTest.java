package handong.whynot.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import handong.whynot.api.v1.CommentController;
import handong.whynot.repository.AccountRepository;
import handong.whynot.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class CommentControllerTest {

    @MockBean private CommentService commentService;
    @MockBean private AccountRepository accountRepository;
    @MockBean private PasswordEncoder passwordEncoder;

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @DisplayName("댓글 조회 [성공]")
    @Test
    void getCommentsByPostIdTest() throws Exception {

        mockMvc.perform(get("/v1/comments/{postId}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

}
