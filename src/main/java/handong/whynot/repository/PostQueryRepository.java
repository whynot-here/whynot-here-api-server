package handong.whynot.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import handong.whynot.domain.*;
import handong.whynot.dto.post.PostResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    private final QPost qPost = QPost.post;
    private final QJobPost qJobPost = QJobPost.jobPost;
    private final QJob qJob = QJob.job;
    private final QPostApply qPostApply = QPostApply.postApply;
    private final QUser qUser = QUser.user;

    // Post Full info
    public List<PostResponseDTO> getPosts() {

        // dto 하나더 만들기는 그래서 이렇게 fields로 넣었음.
        return queryFactory.select(
                    Projections.fields(PostResponseDTO.class,
                            qPost.id, qPost.title, qPost.postImg,
                            qPost.createdDt, qPost.createdBy, qPost.content,
                            qPost.isRecruiting))
                .from(qPost)
                .fetch();
    }

    public List<Job> getJobs(Long post_id) {
        return queryFactory.selectFrom(qJob)
                        .where(qJob.id.in(
                                select(qJobPost.job.id).from(qJobPost)
                                        .where(qJobPost.post.id.eq(post_id))
                        ))
                .fetch();
    }

    public List<User> getApplicants(Long post_id) {
        return queryFactory.selectFrom(qUser)
                .where(qUser.id.in(
                        select(qPostApply.user.id).from(qPostApply)
                                .where(qPostApply.post.id.eq(post_id))
                        )
                )
                .fetch();
    }
}
