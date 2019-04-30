package by.epam.provider.dao;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.Tariff;

import java.util.List;

public interface TariffDao extends CrudDao<Tariff, Long> {
    List<Tariff> findAllActive() throws ProviderException;

    Long size() throws ProviderException;
}
