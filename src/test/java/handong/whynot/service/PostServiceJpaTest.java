package handong.whynot.service;

import handong.whynot.domain.JobPost;
import handong.whynot.domain.Post;
import handong.whynot.dto.post.PostRequestDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Disabled
class PostServiceJpaTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private PostService postService;


    @Transactional
    @Test
    @Rollback(value = false)
    void createPost() {
        PostRequestDTO requestDTO = PostRequestDTO.builder()
                .content("content")
//                .postImg("postImg")
//                .jobIds(Arrays.asList(1L, 2L))
                .build();

//        postService.createPost(requestDTO);

        entityManager.flush();
        entityManager.clear();

        Post savedPost = entityManager.find(Post.class, 2L);
        List<JobPost> jobPosts = savedPost.getJobPosts();
        System.out.println(jobPosts.size());
    }
}