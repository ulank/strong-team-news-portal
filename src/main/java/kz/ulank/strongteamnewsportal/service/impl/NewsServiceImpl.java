package kz.ulank.strongteamnewsportal.service.impl;

import kz.ulank.strongteamnewsportal.common.exception.NotFoundException;
import kz.ulank.strongteamnewsportal.entity.News;
import kz.ulank.strongteamnewsportal.entity.Source;
import kz.ulank.strongteamnewsportal.entity.Topic;
import kz.ulank.strongteamnewsportal.model.dto.NewsDto;
import kz.ulank.strongteamnewsportal.repository.NewsRepository;
import kz.ulank.strongteamnewsportal.repository.SourceRepository;
import kz.ulank.strongteamnewsportal.repository.TopicRepository;
import kz.ulank.strongteamnewsportal.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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

    private final NewsRepository newsRepository;
    private final SourceRepository sourceRepository;
    private final TopicRepository topicRepository;
    private final ModelMapper modelMapper;

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

        newEntity.getTopics().forEach(topicDto -> topics.add(modelMapper.map(topicDto, Topic.class)));

        News news = News.builder()
                .id(id)
                .title(newEntity.getTitle())
                .content(newEntity.getContent())
                .author(newEntity.getAuthor())
                .topics(topics)
                .description(newEntity.getDescription())
                .source(sourceRepository.findById(newEntity.getSource().getId()).orElse(modelMapper.map(newEntity.getSource(), Source.class)))
                .urlToImage(newEntity.getUrlToImage())
                .url(newEntity.getUrl())
                .build();

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
}