package handong.whynot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import handong.whynot.api.PostController;
import handong.whynot.dto.post.PostRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class PostServiceMvcTest {

    @MockBean
    private PostService postService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("공고생성 [성공]")
    @Test
    void createPost() throws Exception {

        // given
        PostRequestDTO requestDTO = PostRequestDTO.builder()
                .accountId(1L)
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
}
