package kz.ulank.strongteamnewsportal.service.impl;

import kz.ulank.strongteamnewsportal.common.exception.NotFoundException;
import kz.ulank.strongteamnewsportal.entity.Source;
import kz.ulank.strongteamnewsportal.model.dto.SourceDto;
import kz.ulank.strongteamnewsportal.repository.SourceRepository;
import kz.ulank.strongteamnewsportal.service.SourceService;
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
public class SourceServiceImpl implements SourceService {

    private final SourceRepository sourceRepository;
    private final ModelMapper modelMapper;

    /**
     * Saves the given entity to the data source.
     *
     * @param newEntity The new entity to be saved.
     * @return The saved entity.
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Source save(SourceDto newEntity) {
        Source source = modelMapper.map(newEntity, Source.class);
        return sourceRepository.save(source);
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
    public Source update(SourceDto newEntity, String id) {
        Source source = modelMapper.map(newEntity, Source.class);
        source.setId(id);
        return sourceRepository.save(source);
    }

    /**
     * Deletes the entity with the given ID from the data source.
     *
     * @param id The ID of the entity to be deleted.
     */
    @Override
    public void deleteById(String id) {
        try {
            sourceRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't delete Source with id " + id);
        }
    }

    /**
     * Finds all entities in the data source.
     *
     * @return A list of all entities in the data source.
     */
    @Override
    public List<Source> findAll() {
        return sourceRepository.findAll();
    }

    /**
     * Finds the entity with the given ID in the data source.
     *
     * @param id The ID of the entity to be found.
     * @return The found entity, or NotFoundException exception.
     */
    @Override
    public Source findById(String id) {
        return sourceRepository.findById(id).orElseThrow(() -> new NotFoundException("Source not found with id " + id));
    }
}
