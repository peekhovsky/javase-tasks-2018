package by.epam.provider.factory;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.service.Service;

public interface ServiceFactory {
    <T extends Service> T findService(final Class<T> key) throws ProviderException;
}
