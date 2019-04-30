package by.epam.provider.transaction;

import by.epam.provider.dao.CrudDao;
import by.epam.provider.exception.ProviderException;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;

/**
 * @author Rostislav Pekhovsky 2019
 * @version 0.1
 */
public abstract class ProviderTransaction {

    @Setter
    @Getter
    protected Connection connection;

    public abstract <T extends CrudDao<?, ?>> T createDao(Class<T> dao)
            throws ProviderException;

    public abstract void commit() throws ProviderException;

    public abstract void rollback() throws ProviderException;
}
