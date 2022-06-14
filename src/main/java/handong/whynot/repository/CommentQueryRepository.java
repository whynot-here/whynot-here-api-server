package handong.whynot.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import handong.whynot.domain.Account;
import handong.whynot.domain.Comment;
import handong.whynot.domain.QComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepository {

    private final JPAQueryFactory queryFactory;

    private final QComment qComment = QComment.comment;

    public List<Comment> findCommentsByPostId(Long postId) {

        return queryFactory.selectFrom(qComment)
                .where(qComment.post.id.eq(postId))
                .orderBy(
                        qComment.createdDt.asc()
                ).fetch();
    }

    public Optional<Comment> findCommentsByAccount(Long commentId, Account account) {

        return Optional.ofNullable(queryFactory.selectFrom(qComment)
                .where(qComment.id.eq(commentId)
                        .and(qComment.createdBy.eq(account)))
                .fetchFirst());
    }
}
