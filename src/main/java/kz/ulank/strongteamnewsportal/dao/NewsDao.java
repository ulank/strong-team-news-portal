package kz.ulank.strongteamnewsportal.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import kz.ulank.strongteamnewsportal.entity.News;
import kz.ulank.strongteamnewsportal.util.model.OrderType;
import kz.ulank.strongteamnewsportal.util.model.Pagination;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ulan on 5/13/2023
 */
@Repository
public class NewsDao {
    @PersistenceContext
    EntityManager entityManager;

    final Set<String> fields = new HashSet<>(List.of(new String[]{"publishedAt"}));

    public Pagination findAllWithPaginationAndSorting(int offset, int limit, String field, OrderType orderType) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> criteriaQuery = criteriaBuilder.createQuery(News.class);
        Root<News> root = criteriaQuery.from(News.class);

        return getPagination(offset, limit, field, orderType, criteriaBuilder, criteriaQuery, root);
    }

    public Pagination findBySourceIdWithPaginationAndSorting(String sourceId, int offset, int limit, String field, OrderType orderType) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> criteriaQuery = criteriaBuilder.createQuery(News.class);
        Root<News> root = criteriaQuery.from(News.class);
        Predicate sourcePredicate = criteriaBuilder.equal(root.get("source").get("id"), sourceId);
        criteriaQuery.where(sourcePredicate);
        return getPagination(offset, limit, field, orderType, criteriaBuilder, criteriaQuery, root);
    }

    public Pagination findByTopicIdWithPaginationAndSorting(Long topicId, int offset, int limit, String field, OrderType orderType) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> criteriaQuery = criteriaBuilder.createQuery(News.class);
        Root<News> root = criteriaQuery.from(News.class);
        Predicate topicPredicate = criteriaBuilder.equal(root.get("topics").get("id"), topicId);
        criteriaQuery.where(topicPredicate);
        return getPagination(offset, limit, field, orderType, criteriaBuilder, criteriaQuery, root);
    }

    private Pagination getPagination(int offset, int limit, String field, OrderType orderType, CriteriaBuilder criteriaBuilder, CriteriaQuery<News> criteriaQuery, Root<News> root) {
        if (orderType != null) {
            switch (orderType) {
                case ASC ->
                        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(fields.contains(field) ? field : "publishedAt")));
                case DESC ->
                        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(fields.contains(field) ? field : "publishedAt")));
            }
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(fields.contains(field) ? field : "publishedAt")));
        }

        TypedQuery<News> query = entityManager.createQuery(criteriaQuery);

        List<News> news = query.setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

        return Pagination.builder().articles(news).page(offset).pageSize(limit).totalResults(news.size()).build();
    }

}
