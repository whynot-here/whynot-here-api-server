package handong.whynot.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import handong.whynot.domain.*;
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
    private final QAccount qAccount = QAccount.account;
    private final QPostFavorite qPostFavorite = QPostFavorite.postFavorite;

    // Post Full info
    public List<Post> getPosts() {

        // dto 하나더 만들기는 그래서 이렇게 fields로 넣었음.
        return queryFactory.selectFrom(qPost)
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

    public List<Account> getApplicants(Long post_id) {
        return queryFactory.selectFrom(qAccount)
                .where(qAccount.id.in(
                                select(qPostApply.account.id).from(qPostApply)
                                        .where(qPostApply.post.id.eq(post_id))
                        )
                )
                .fetch();
    }

    public List<Post> getApplys(Account account) {

        Long accountId = account.getId();

        return queryFactory.selectFrom(qPost)
                .where(qPost.id.in(
                        select(qPostApply.post.id).from(qPostApply)
                                .where(qPostApply.account.id.eq(accountId))
                ))
                .fetch();
    }
}
