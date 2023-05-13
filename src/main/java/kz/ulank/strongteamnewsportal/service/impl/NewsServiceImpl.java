package kz.ulank.strongteamnewsportal.service.impl;

import kz.ulank.strongteamnewsportal.dao.NewsDao;
import kz.ulank.strongteamnewsportal.common.exception.NotFoundException;
import kz.ulank.strongteamnewsportal.entity.News;
import kz.ulank.strongteamnewsportal.entity.Source;
import kz.ulank.strongteamnewsportal.entity.Topic;
import kz.ulank.strongteamnewsportal.integration.response.Articles;
import kz.ulank.strongteamnewsportal.integration.response.EverythingResponse;
import kz.ulank.strongteamnewsportal.integration.response.TopHeadlinesResponse;
import kz.ulank.strongteamnewsportal.integration.service.NewsApiService;
import kz.ulank.strongteamnewsportal.model.dto.NewsDto;
import kz.ulank.strongteamnewsportal.model.dto.SourceDto;
import kz.ulank.strongteamnewsportal.repository.NewsRepository;
import kz.ulank.strongteamnewsportal.repository.SourceRepository;
import kz.ulank.strongteamnewsportal.repository.TopicRepository;
import kz.ulank.strongteamnewsportal.service.NewsService;
import kz.ulank.strongteamnewsportal.util.model.OrderType;
import kz.ulank.strongteamnewsportal.util.model.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ulan on 5/12/2023
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final ModelMapper modelMapper;
    private final NewsRepository newsRepository;
    private final NewsDao newsDao;
    private final SourceRepository sourceRepository;
    private final TopicRepository topicRepository;
    private final NewsApiService newsApiService;

    /**
     * Saves the given entity to the data source.
     *
     * @param newEntity The new entity to be saved.
     * @return The saved entity.
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public News save(NewsDto newEntity) {
        List<Topic> topics = new ArrayList<>();

        newEntity.getTopics().forEach(topicDto -> topics.add(topicRepository.findTopicByNameStartsWith(topicDto.getName()).orElse(modelMapper.map(topicDto, Topic.class))));

        News news = News.builder()
                .title(newEntity.getTitle())
                .content(newEntity.getContent())
                .author(newEntity.getAuthor())
                .description(newEntity.getDescription())
                .topics(topics)
                .source(sourceRepository.findById(newEntity.getSource().getId()).orElse(modelMapper.map(newEntity.getSource(), Source.class)))
                .urlToImage(newEntity.getUrlToImage())
                .url(newEntity.getUrl())
                .publishedAt(ZonedDateTime.now())
                .published(true)
                .build();

        return newsRepository.save(news);
    }

    /**
     * Updates the entity with the given ID with the data from the new entity.
     *
     * @param newEntity The updated entity to replace the existing one.
     * @param id        The ID of the entity to be updated.
     * @return The updated entity.
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public News update(NewsDto newEntity, UUID id) {

        List<Topic> topics = new ArrayList<>();

        newEntity.getTopics().forEach(topicDto -> topics.add(topicRepository.findTopicByNameStartsWith(topicDto.getName()).orElse(modelMapper.map(topicDto, Topic.class))));

        News news = findById(id);
        news.setTitle(newEntity.getTitle());
        news.setContent(newEntity.getContent());
        news.setAuthor(newEntity.getAuthor());
        news.setTopics(topics);
        news.setDescription(newEntity.getDescription());
        news.setSource(sourceRepository.findById(newEntity.getSource().getId()).orElse(modelMapper.map(newEntity.getSource(), Source.class)));
        news.setUrlToImage(newEntity.getUrlToImage());
        news.setUrl(newEntity.getUrl());
        news.setPublished(true);

        return newsRepository.save(news);
    }

    /**
     * Deletes the entity with the given ID from the data source.
     *
     * @param id The ID of the entity to be deleted.
     */
    @Override
    public void deleteById(UUID id) {
        try {
            newsRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't delete News with id " + id);
        }
    }

    /**
     * Finds all entities in the data source.
     *
     * @return A list of all entities in the data source.
     */
    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    /**
     * Finds the entity with the given ID in the data source.
     *
     * @param id The ID of the entity to be found.
     * @return The found entity, or NotFoundException exception.
     */
    @Override
    public News findById(UUID id) {
        return newsRepository.findById(id).orElseThrow(() -> new NotFoundException("News not found with id " + id));
    }


    /**
     * This method saves news articles using the provided slug from News API. It retrieves the articles using the slug, maps the data to a News object, and saves the News object to the news repository. It returns a list of the saved news articles.
     *
     * @param slug the slug used to retrieve news articles from the News API
     * @return a list of the saved news articles
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<News> saveNewsUsingSlugByNewsApi(String slug) {

        List<News> news = new ArrayList<>();

        EverythingResponse everythingBySlug = newsApiService.getEverythingBySlug(slug);

        everythingBySlug.getArticles().forEach((Articles articles) -> addNewsFromResponse(news, articles.getSource().getId(), articles.getSource().getName(), articles.getTitle(), articles.getContent(), articles.getAuthor(), articles.getDescription(), articles.getUrlToImage(), articles.getUrl(), articles.getPublishedAt()));

        return newsRepository.saveAll(news);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<News> saveNewsByNewsApi(String country) {

        List<News> news = new ArrayList<>();

        TopHeadlinesResponse topHeadlinesResponse = newsApiService.getTopHeadlinesResponse(country);

        topHeadlinesResponse.getArticles().forEach((Articles articles) -> {
            log.info(articles.toString());
            addNewsFromResponse(news, articles.getSource().getId(), articles.getSource().getName(), articles.getTitle(), articles.getContent(), articles.getAuthor(), articles.getDescription(), articles.getUrlToImage(), articles.getUrl(), articles.getPublishedAt());
        });


        return newsRepository.saveAll(news);
    }

    private void addNewsFromResponse(List<News> news, String id, String name, String title, String content, String author, String description, String urlToImage, String url, String publishedAt) {
        Source source;

        if (id != null) {
            source = sourceRepository.findById(id).orElse(sourceRepository.save(modelMapper.map(new SourceDto(id, name, null), Source.class)));
        } else {
            source = sourceRepository.findSourceByNameStartsWith(name).orElse(sourceRepository.save(modelMapper.map(new SourceDto(name.toLowerCase(), name, null), Source.class)));
        }

        if (newsRepository.findByTitleStartsWith(title).isEmpty())
            news.add(News.builder()
                    .title(title)
                    .content(content)
                    .author(author)
                    .description(description)
                    .source(source)
                    .urlToImage(urlToImage)
                    .url(url)
                    .published(true)
                    .publishedAt(ZonedDateTime.parse(publishedAt))
                    .build());

    }


    /**
     * This method retrieves a paginated list of news articles with sorting. It retrieves the news articles from the news repository and returns them as a Pagination object.
     *
     * @param offset   the number of articles to retrieve per page
     * @param limit    the page number to retrieve
     * @param sortBy   the sorting field - default publishedAt
     * @param sortType the sort order to apply (ascending or descending)
     * @return a Pagination object containing the retrieved news articles
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Pagination findAllWithPaginationAndSorting(int offset, int limit, String sortBy, OrderType sortType) {
        return newsDao.findAllWithPaginationAndSorting(offset, limit, sortBy, sortType);
    }

    /**
     * This method retrieves news articles from a specific source with pagination and sorting applied. It retrieves the news articles from the data access object (DAO) using the provided source ID, offset, limit, sorting field, and sort order. It returns a Pagination object containing the retrieved news articles.
     *
     * @param sourceId the ID of the source to retrieve news articles from
     * @param offset   the offset to start retrieving articles from
     * @param limit    the maximum number of articles to retrieve per page
     * @param sortBy   the field to sort the articles by (default: publishedAt)
     * @param sortType the sort order to apply (ascending or descending)
     * @return a Pagination object containing the retrieved news articles
     */
    @Override
    public Pagination findBySourceIdWithPaginationAndSorting(String sourceId, int offset, int limit, String sortBy, OrderType sortType) {
        return newsDao.findBySourceIdWithPaginationAndSorting(sourceId, offset, limit, sortBy, sortType);
    }


    /**
     * This method retrieves news articles related to a specific topic with pagination and sorting applied. It retrieves the news articles from the data access object (DAO) using the provided topic ID, offset, limit, sorting field, and sort order. It returns a Pagination object containing the retrieved news articles.
     *
     * @param topicId  the ID of the topic to retrieve news articles for
     * @param offset   the offset to start retrieving articles from
     * @param limit    the maximum number of articles to retrieve per page
     * @param sortBy   the field to sort the articles by (default: publishedAt)
     * @param sortType the sort order to apply (ascending or descending)
     * @return a Pagination object containing the retrieved news articles
     */
    @Override
    public Pagination findByTopicIdWithPaginationAndSorting(Long topicId, int offset, int limit, String sortBy, OrderType sortType) {
        return newsDao.findByTopicIdWithPaginationAndSorting(topicId, offset, limit, sortBy, sortType);
    }

}
