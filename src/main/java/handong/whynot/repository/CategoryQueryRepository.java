package handong.whynot.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import handong.whynot.domain.Category;
import handong.whynot.domain.QCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    private final QCategory qCategory = QCategory.category;

    public List<Category> getDefaultList() {

        return queryFactory.selectFrom(qCategory)
                .where(qCategory.useYn.eq("Y"))
                .orderBy(qCategory.order.asc())
                .fetch();
    }
}
