package by.epam.task1.repository;

import java.util.List;
import java.util.Optional;

/**
 * There is simple interface for repository.
 *
 * @param <T> any class whose objects are stored in repository.
 * @author Rostislav Pekhovsky (Rastsislau Piakhouski) 2018
 * @version 0.1
 */
public interface Repository<T> {
    /**
     * That method is to add new object to storage.
     *
     * @param model new object
     * @return id
     */
    int add(T model);

    /**
     * That method is to add a list of objects to storage.
     *
     * @param model new list of objects
     */
    void addAll(List<T> model);

    /**
     * That method is to delete an object by its id.
     *
     * @param id object id
     */
    void delete(Integer id);

    /**
     * That method is to delete all elements from collection.
     */
    void deleteAll();

    /**
     * That method update sphere (sets a new sphere) using its id.
     *
     * @param id    sphere id
     * @param model new sphere
     */
    void update(Integer id, T model);

    /**
     * This method finds an object using its id.
     *
     * @param id object id
     * @return present optional if object has been found,
     * otherwise empty optional
     */
    Optional<T> findById(Integer id);

    /**
     * This method returns every object is a storage.
     *
     * @return list with all objects
     */
    List<T> findAll();
}

