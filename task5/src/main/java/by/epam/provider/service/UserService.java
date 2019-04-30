package by.epam.provider.service;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.Discount;
import by.epam.provider.model.User;
import by.epam.provider.model.UserInfo;
import by.epam.provider.model.dto.UserDto;
import by.epam.provider.model.form.UserForm;

import java.util.List;
import java.util.Optional;

public interface UserService extends Service<User> {

    Optional<User> findUserByLogin(final String id) throws ProviderException;

    Optional<User> findUserByLoginAndCheckPassword(UserForm userForm) throws ProviderException;

    Optional<UserDto> findUserDtoById(long id) throws ProviderException;

    Optional<UserInfo> findUserInfoById(final long id) throws ProviderException;

    void addClientUser(final UserForm userForm) throws ProviderException;

    void updateUserTariff(long userId, long tariffId)
            throws ProviderException;

    void updateUserLogo(long userId, String path)
            throws ProviderException;

    void updateLoginNameSurname(long userId, String login,
                                String firstName,
                                String lastName)
            throws ProviderException;

    void addMoneyToBalance(long userId, long amount)
            throws ProviderException;

    void chargeUsers() throws ProviderException;

    void blockUsersWithNegativeBalance() throws ProviderException;

    void banUserById(long usedId) throws ProviderException;

    void unbanUserById(long usedId) throws ProviderException;

    boolean addDiscount(long userId, long discountId) throws ProviderException;

    List<Discount> discountsByUserId(long id) throws ProviderException;
}
