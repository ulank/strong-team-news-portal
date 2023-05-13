package kz.ulank.strongteamnewsportal.service.impl;

import kz.ulank.strongteamnewsportal.common.exception.NotFoundException;
import kz.ulank.strongteamnewsportal.entity.News;
import kz.ulank.strongteamnewsportal.entity.Source;
import kz.ulank.strongteamnewsportal.entity.Topic;
import kz.ulank.strongteamnewsportal.integration.enums.EverythingLang;
import kz.ulank.strongteamnewsportal.integration.response.EverythingResponse;
import kz.ulank.strongteamnewsportal.integration.service.NewsApiService;
import kz.ulank.strongteamnewsportal.model.dto.NewsDto;
import kz.ulank.strongteamnewsportal.model.dto.SourceDto;
import kz.ulank.strongteamnewsportal.repository.NewsRepository;
import kz.ulank.strongteamnewsportal.repository.SourceRepository;
import kz.ulank.strongteamnewsportal.repository.TopicRepository;
import kz.ulank.strongteamnewsportal.service.NewsService;
import kz.ulank.strongteamnewsportal.util.model.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

        log.info(news.toString());
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

        everythingBySlug.getArticles().forEach((EverythingResponse.Articles articles) -> {

            Source source;

            if (articles.getSource().getId() != null) {
                source = sourceRepository.findById(articles.getSource().getId()).orElse(sourceRepository.save(modelMapper.map(new SourceDto(articles.getSource().getId(), articles.getSource().getName(), null), Source.class)));
            } else {
                source = sourceRepository.findSourceByNameStartsWith(articles.getSource().getName()).orElse(sourceRepository.save(modelMapper.map(new SourceDto(articles.getSource().getName().toLowerCase(), articles.getSource().getName(), null), Source.class)));
            }

            if (newsRepository.findByTitleStartsWith(articles.getTitle()).isEmpty())
                news.add(News.builder()
                        .title(articles.getTitle())
                        .content(articles.getContent())
                        .author(articles.getAuthor())
                        .description(articles.getDescription())
                        .source(source)
                        .urlToImage(articles.getUrlToImage())
                        .url(articles.getUrl())
                        .published(true)
                        .publishedAt(ZonedDateTime.parse(articles.getPublishedAt()))
                        .build());
        });


        return newsRepository.saveAll(news);
    }

    /**
     * This method retrieves a paginated list of news articles by their published status. It retrieves the news articles from the news repository and returns them as a Pagination object.
     *
     * @param published the published status of the news articles to retrieve
     * @param page      the page number to retrieve
     * @param size      the number of articles to retrieve per page
     * @return a Pagination object containing the retrieved news articles
     */
    @Override
    public Pagination findAllByPublished(boolean published, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<News> pageNews = newsRepository.findNewsByPublished(published, paging);
        List<News> news = pageNews.getContent();
        Pagination build = Pagination.builder().articles(news).page(page).pageSize(size).totalResults(news.size()).build();
        log.debug(build.toString());
        return build;
    }
}
