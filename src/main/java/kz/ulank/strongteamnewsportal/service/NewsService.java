package kz.ulank.strongteamnewsportal.service;

import kz.ulank.strongteamnewsportal.entity.News;
import kz.ulank.strongteamnewsportal.model.dto.NewsDto;
import kz.ulank.strongteamnewsportal.util.model.OrderType;
import kz.ulank.strongteamnewsportal.util.model.Pagination;
import kz.ulank.strongteamnewsportal.util.operation.CrudOperation;

import java.util.List;
import java.util.UUID;

/**
 * Created by Ulan on 5/12/2023
 */
public interface NewsService extends CrudOperation<News, NewsDto, UUID> {

    List<News> saveNewsUsingSlugByNewsApi(String slug);

    List<News> saveNewsByNewsApi(String country);

    Pagination findAllWithPaginationAndSorting(int offset, int limit, String sortBy, OrderType sortType);
    Pagination findBySourceIdWithPaginationAndSorting(String sourceId, int offset, int limit, String sortBy, OrderType sortType);

    Pagination findByTopicIdWithPaginationAndSorting(Long topicId, int offset, int limit, String sortBy, OrderType sortType);
}
