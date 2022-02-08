package handong.whynot.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import handong.whynot.common.WithMockCustomUser;
import handong.whynot.domain.Account;
import handong.whynot.domain.Post;
import handong.whynot.dto.post.PostRequestDTO;
import handong.whynot.dto.post.PostResponseDTO;
import handong.whynot.repository.AccountRepository;
import handong.whynot.repository.PostQueryRepository;
import handong.whynot.repository.PostRepository;
import handong.whynot.service.AccountService;
import handong.whynot.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(PostController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class PostServiceMvcTest {

    @MockBean private PostService postService;
    @MockBean private PostRepository postRepository;
    @MockBean private AccountService accountService;
    @MockBean private AccountRepository accountRepository;
    @MockBean private PostQueryRepository postQueryRepository;

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {

        Account account = Account.builder()
                .id(1L)
                .nickname("whynot-user")
                .email("whynot-user" + "@email.com")
                .password("12345678")
                .build();
        account.completeSignUp();
        when(accountRepository.findById(anyLong())).thenReturn(Optional.ofNullable(account));

        Post post = Post.builder()
                        .id(1L)
                        .createdBy(account)
                        .title("공프기 모집")
                        .content("개발자, 디자이너가 필요해요~!!").build();
        PostResponseDTO dto = PostResponseDTO.of(post, new ArrayList<>(), new ArrayList<>());
        when(postService.getPost(1L)).thenReturn(dto);

        MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @DisplayName("공고생성 [성공]")
    @Test
    @WithMockCustomUser
    void createPost() throws Exception {

        // given
        PostRequestDTO requestDTO = PostRequestDTO.builder()
                .title("[공고 모집] 캡스톤 함께하실 분!")
                .content("행복한 펫을 위해 서비스를 만들고자 합니다.")
                .postImg("http://image.com")
                .jobIds(Arrays.asList(2L, 3L))
                .build();

        // when, given
        mockMvc.perform(post("/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("statusCode").value(20001))
        ;
    }

    @DisplayName("공고 단건 조회")
    @Test
    void getPostTest() throws Exception{

        mockMvc.perform(get("/v1/posts/{postId}", 1L))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("공고 단건 삭제")
    @Test
    @WithMockCustomUser
    void deletePostTest() throws Exception {

        mockMvc.perform(delete("/v1/posts/{postId}", 1L))
                .andExpect(jsonPath("statusCode").value(20004))
                .andDo(print());

    }
}
