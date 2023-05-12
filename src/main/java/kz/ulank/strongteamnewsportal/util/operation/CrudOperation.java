package kz.ulank.strongteamnewsportal.util.operation;


import java.util.List;


/**
 * @param <T> The entity type that this interface is capable of operating on.
 * @param <K> The key type that uniquely identifies each entity in the data source.
 * @author Ulan
 *
 * <p>
 * Descrption : This interface defines a set of CRUD (Create, Read, Update, Delete) operations that can be performed on a data source.
 * </p>
 */
public interface CrudOperation<T, D, K> {

    /**
     * Saves the given entity to the data source.
     *
     * @param newEntity The new entity to be saved.
     * @return The saved entity.
     */
    T save(D newEntity);

    /**
     * Updates the entity with the given ID with the data from the new entity.
     *
     * @param newEntity The updated entity to replace the existing one.
     * @param id        The ID of the entity to be updated.
     * @return The updated entity.
     */
    T update(D newEntity, K id);

    /**
     * Deletes the entity with the given ID from the data source.
     *
     * @param id The ID of the entity to be deleted.
     */
    void deleteById(K id);

    /**
     * Finds all entities in the data source.
     *
     * @return A list of all entities in the data source.
     */
    List<T> findAll();

    /**
     * Finds the entity with the given ID in the data source.
     *
     * @param id The ID of the entity to be found.
     * @return The found entity, or null if not found.
     */
    T findById(K id);

}
