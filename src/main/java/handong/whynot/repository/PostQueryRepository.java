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

    public List<Post> getFavorites(Account account) {
        Long accountId = account.getId();

        return queryFactory.selectFrom(qPost)
                .where(qPost.id.in(
                        select(qPostFavorite.post.id).from(qPostFavorite)
                                .where(qPostFavorite.account.id.eq(accountId))
                ))
                .fetch();
    }


    public List<PostFavorite> getFavoriteByPostId(Post post, Account account) {

        Long postId = post.getId();
        Long accountId = account.getId();

        return queryFactory.select(qPostFavorite)
                .from(qPost, qPostFavorite)
                .where(qPostFavorite.account.id.eq(accountId)
                        .and(qPostFavorite.post.id.eq(postId)))

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
  
    public List<PostApply> getApplyByPostId(Post post, Account account) {

        Long postId = post.getId();
        Long accountId = account.getId();

        return queryFactory.select(qPostApply)
                .from(qPost, qPostApply)
                .where(qPostApply.account.id.eq(accountId)
                        .and(qPostApply.post.id.eq(postId)))
                .fetch();
    }

    public List<Post> getPostByRecruit(Boolean isRecruiting) {

        return queryFactory.selectFrom(qPost)
                .where(qPost.isRecruiting.eq(isRecruiting))
                .fetch();
    }

    public List<Post> getPostByRecruitAndJob(Boolean isRecruiting, List<Job> jobs) {

        return queryFactory.select(qPost)
                .from(qPost, qJobPost)
                .where(qPost.isRecruiting.eq(isRecruiting)
                        .and(qJobPost.job.in(jobs))
                )
                .fetch();
    }

    public List<Post> getEnabledPost(Long postId) {

        return queryFactory.selectFrom(qPost)
                .where(qPost.id.eq(postId)
                        .and(qPost.isRecruiting))
                .fetch();
    }

    public List<Post> getPostByJob(List<Job> jobs) {

        return queryFactory.select(qPost)
                .from(qPost, qJobPost)
                .where(qJobPost.job.in(jobs))
                .fetch();
    }
}