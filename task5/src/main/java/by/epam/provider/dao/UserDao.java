package by.epam.provider.dao;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.User;
import by.epam.provider.model.dto.UserDto;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface UserDao extends CrudDao<User, Long> {

    List<UserDto> findAllUsersWithInfo() throws ProviderException;

    Optional<User> findUserByLogin(String login) throws ProviderException;

    Long size() throws ProviderException;
}
