package by.epam.provider.dao.jdbc;

import by.epam.provider.dao.UserDao;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.User;
import by.epam.provider.model.dto.UserDto;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static by.epam.provider.dao.jdbc.DaoRegular.*;


/**
 * @author Rostislav Pekhovsky
 * @version 0.1
 */
@NoArgsConstructor
@Log4j2
public final class UserDaoImpl extends AbstractDaoImpl implements UserDao {

    //language=SQL
    private static final String SQL_ADD_USER_TO_USER
            = "INSERT INTO user (login, hash_password, salt_password,"
            + " type, status) VALUES (?, ?, ?, ?, ?)";

    //language=SQL
    private static final String SQL_UPD_USER
            = "UPDATE user SET login = ?, hash_password = ?, salt_password = ?,"
            + " type = ?, status = ? WHERE id = ?";

    //language=SQL
    private static final String SQL_FIND_ALL_USERS
            = "SELECT * FROM user";

    //language=SQL
    private static final String SQL_FIND_ALL_USERS_LIMIT
            = "SELECT * FROM user LIMIT ?, ?";

    //language=SQL
    private static final String SQL_FIND_ALL_USERS_WITH_INFO
            = "SELECT * FROM user LEFT JOIN user_info "
            + "ON user.info_id = user_info.id";

    //language=SQL
    private static final String SQL_FIND_USER_BY_ID
            = "SELECT * FROM user WHERE id = ?";

    //language=SQL
    private static final String SQL_FIND_USER_BY_LOGIN
            = "SELECT * FROM user WHERE login = ?";

    //language=SQL
    private static final String SQL_USER_ROW_NUMBER
            = "SELECT COUNT(*) FROM user";

    public UserDaoImpl(Connection connection) {
        super(connection);
    }

    /**
     * This method adds a new model in a table.
     *
     * @param model model to add
     * @return true if model has been added, otherwise false
     */
    @Override
    public Long create(User model) throws ProviderException {
        try (PreparedStatement statement
                     = this.connection.prepareStatement(
                SQL_ADD_USER_TO_USER,
                Statement.RETURN_GENERATED_KEYS)) {

            createStatementFromModel(model, statement);

            statement.execute();
            long userInfoKey = receiveKeyAfterExecute(statement);
            log.trace("Added data to user table, key: " + userInfoKey);
            return userInfoKey;

        } catch (SQLException e) {
            log.error("Cannot add data to user table");
            log.error(e.getMessage());
            throw new ProviderException(e);
        }
    }

    /**
     * This method updates a model in a table (by id from model value).
     *
     * @param model model in a table to update
     * @return true if model has been updated, otherwise false
     */
    @Override
    public void update(User model) throws ProviderException {
        try (PreparedStatement statement
                     = this.connection.prepareStatement(SQL_UPD_USER)) {

            createStatementFromModel(model, statement);

            statement.setLong(6, model.getId());
            statement.execute();

        } catch (SQLException e) {
            log.error("Cannot add data to user table");
            log.error(e.getMessage());
            throw new ProviderException(e);
        }
    }

    /**
     * This method returns all models from table.
     *
     * @return a list of all models from table
     */
    @Override
    public List<User> findAll() throws ProviderException {
        List<User> users = new LinkedList<>();

        try (Statement statement = connection.createStatement()) {

            try (ResultSet rs = statement.executeQuery(SQL_FIND_ALL_USERS)) {
                while (rs.next()) {
                    users.add(userFromResultSet(rs));
                }
            }
            return users;
        } catch (SQLException e) {
            throw new ProviderException(e);
        }
    }

    /**
     * This method returns all models from table in preset range
     * (for page representation).
     *
     * @param startValue start value
     * @param limit      limit
     * @return a list of all models from table in range
     */
    @Override
    public List<User> findAllInRange(Long startValue, Long limit)
            throws ProviderException {
        List<User> users = new ArrayList<>();

        try (PreparedStatement statement = this.connection
                .prepareStatement(SQL_FIND_ALL_USERS_LIMIT)) {

            statement.setLong(1, startValue);
            statement.setLong(2, limit);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    users.add(userFromResultSet(rs));
                }
            }
            return users;
        } catch (SQLException e) {
            throw new ProviderException(e);
        }
    }

    /**
     * This method returns a model from table by its id.
     *
     * @param id id to find model
     * @return present optional if model has been found,
     * otherwise empty optional.
     */
    @SuppressWarnings("Duplicates")
    @Override
    public Optional<User> findById(final Long id) throws ProviderException {
        Optional<User> optionalUser;

        try (PreparedStatement statement = this.connection
                .prepareStatement(SQL_FIND_USER_BY_ID)) {

            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    optionalUser = Optional.of(userFromResultSet(rs));
                } else {
                    optionalUser = Optional.empty();
                }
            }
            return optionalUser;

        } catch (SQLException e) {
            throw new ProviderException(e);
        }
    }

    /**
     * This method deletes a model from table by its id.
     *
     * @param id id to delete model
     * @return true if model has been deleted, otherwise false
     */
    @Override
    public boolean removeById(Long id) throws ProviderException {
        throw new ProviderException(new UnsupportedOperationException(), 501);
    }

    /**
     * This method deletes a model from table.
     *
     * @param model model to delete
     * @return true if model has been deleted, otherwise false
     */
    @Override
    public boolean remove(User model) throws ProviderException {
        throw new ProviderException(new UnsupportedOperationException(), 501);
    }


    @Override
    public List<UserDto> findAllUsersWithInfo() throws ProviderException {
        List<UserDto> users = new LinkedList<>();

        try (Statement statement = this.connection.createStatement()) {

            try (ResultSet rs = statement.executeQuery(
                    SQL_FIND_ALL_USERS_WITH_INFO)) {
                while (rs.next()) {
                    users.add(userDtoFromResultSet(rs));
                }
            }
            return users;
        } catch (SQLException e) {
            throw new ProviderException(e);
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<User> findUserByLogin(String login) throws ProviderException {
        Optional<User> optionalUser;

        try (PreparedStatement statement = this.connection
                .prepareStatement(SQL_FIND_USER_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    optionalUser = Optional.of(userFromResultSet(rs));
                } else {
                    optionalUser = Optional.empty();
                }
            }
            return optionalUser;

        } catch (SQLException e) {
            throw new ProviderException(e);
        }
    }

    @Override
    @SuppressWarnings("Duplicates")
    public Long size() throws ProviderException {
        return DaoRegular.size(SQL_USER_ROW_NUMBER, this.connection);
    }


    private void createStatementFromModel(User model, PreparedStatement statement)
            throws SQLException {
        log.info(model);
        statement.setString(1, model.getLogin());
        statement.setString(2, model.getHashPassword());
        statement.setString(3, model.getSaltPassword());
        statement.setString(4, model.getType().getValue());
        statement.setString(5, model.getStatus().name());
    }

}
