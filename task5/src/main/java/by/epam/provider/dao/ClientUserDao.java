package by.epam.provider.dao;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.Bill;
import by.epam.provider.model.ClientUser;

import java.util.List;
import java.util.Optional;

public interface ClientUserDao extends CrudDao<ClientUser, Long> {

    void updateBlockUsersWithNegativeBalance() throws ProviderException;

    void addBalance(long amount, long userId) throws ProviderException;

    List<Bill> chargeClientUsers() throws ProviderException;

    @SuppressWarnings("Duplicates")
    Optional<Bill> chargeClientUser(long id, float discount) throws ProviderException;

    Long size() throws ProviderException;
}
