package by.epam.provider.dao;

import by.epam.provider.exception.ProviderException;

import java.util.List;
import java.util.Optional;

/**
 * Crud Data Access Object Interface.
 *
 * @param <T> model
 * @param <K> id (or key) value to access model
 * @author Rostislav Pekhovsky 2019
 * @version 0.1
 */
public interface CrudDao<T, K> {
    /**
     * This method adds a new model in a table.
     *
     * @param model model to add
     * @return true if model has been added, otherwise false
     */
    K create(T model) throws ProviderException;

    /**
     * This method updates a model in a table (by id from model value).
     *
     * @param model model in a table to update
     * @return true if model has been updated, otherwise false
     */
    void update(T model) throws ProviderException;

    /**
     * This method returns all models from table.
     *
     * @return a list of all models from table
     */
    List<T> findAll() throws ProviderException;

    /**
     * This method returns all models from table in preset range
     * (for page representation).
     *
     * @param startValue start value
     * @param endValue   end value
     * @return a list of all models from table in range
     */
    List<T> findAllInRange(K startValue, K endValue) throws ProviderException;

    /**
     * This method returns a model from table by its id.
     *
     * @param id id to find model
     * @return present optional if model has been found,
     * otherwise empty optional.
     */
    Optional<T> findById(K id) throws ProviderException;

    /**
     * This method deletes a model from table by its id.
     *
     * @param id id to delete model
     * @return true if model has been deleted, otherwise false
     */
    boolean removeById(K id) throws ProviderException;

    /**
     * This method deletes a model from table.
     *
     * @param model model to delete
     * @return true if model has been deleted, otherwise false
     */
    boolean remove(T model) throws ProviderException;
}
