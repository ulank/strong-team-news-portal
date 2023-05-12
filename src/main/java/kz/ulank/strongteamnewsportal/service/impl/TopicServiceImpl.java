package kz.ulank.strongteamnewsportal.service.impl;

import kz.ulank.strongteamnewsportal.common.exception.NotFoundException;
import kz.ulank.strongteamnewsportal.entity.Topic;
import kz.ulank.strongteamnewsportal.model.dto.TopicDto;
import kz.ulank.strongteamnewsportal.repository.TopicRepository;
import kz.ulank.strongteamnewsportal.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Ulan on 5/12/2023
 */
@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

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
    public Topic save(TopicDto newEntity) {
        Topic topic = modelMapper.map(newEntity, Topic.class);
        return topicRepository.save(topic);
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
    public Topic update(TopicDto newEntity, Long id) {
        Topic topic = modelMapper.map(newEntity, Topic.class);
        topic.setId(id);
        return topicRepository.save(topic);
    }

    /**
     * Deletes the entity with the given ID from the data source.
     *
     * @param id The ID of the entity to be deleted.
     */
    @Override
    public void deleteById(Long id) {
        try {
            topicRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't delete Topic with id " + id);
        }
    }

    /**
     * Finds all entities in the data source.
     *
     * @return A list of all entities in the data source.
     */
    @Override
    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    /**
     * Finds the entity with the given ID in the data source.
     *
     * @param id The ID of the entity to be found.
     * @return The found entity, or NotFoundException exception.
     */
    @Override
    public Topic findById(Long id) {
        return topicRepository.findById(id).orElseThrow(() -> new NotFoundException("Topic not found with id " + id));
    }
}
