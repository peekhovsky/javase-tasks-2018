package by.epam.provider.service;

import by.epam.provider.dao.TariffDao;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.ClientUser;
import by.epam.provider.model.Tariff;

import java.util.List;
import java.util.Optional;

public interface TariffService extends Service<Tariff> {
    List<Tariff> findAllActive() throws ProviderException;

    void blockTariff(long tariffId) throws ProviderException;

    void unblockTariff(long tariffId) throws ProviderException;
}
