package by.epam.provider.service;

import by.epam.provider.exception.ProviderException;

import java.util.List;
import java.util.Optional;

public interface Service<T> {
    List<T> findAll() throws ProviderException;

    List<T> findAllInRange(long num, long pageSize)
            throws ProviderException;

    Optional<T> findById(long id) throws ProviderException;

    void add(T model) throws ProviderException;

    void update(T model) throws ProviderException;

    void removeById(long id) throws ProviderException;

    long size() throws ProviderException;
}
