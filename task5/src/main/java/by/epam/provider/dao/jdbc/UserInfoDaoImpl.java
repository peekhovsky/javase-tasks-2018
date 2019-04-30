package by.epam.provider.dao.jdbc;

import by.epam.provider.dao.UserInfoDao;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.UserInfo;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static by.epam.provider.dao.jdbc.DaoRegular.userInfoFromResultSet;


/**
 * @author Rostislav Pekhovsky
 * @version 0.1
 */
@NoArgsConstructor
@Log4j2
public final class UserInfoDaoImpl extends AbstractDaoImpl implements UserInfoDao {


    //language=SQL
    private static final String SQL_ADD_USER_TO_USER_INFO
            = "INSERT INTO user_info (user_id, first_name, last_name, "
            + "registration_date, icon_reference)"
            + "VALUES (?, ?, ?, ?, ?)";

    //language=SQL
    private static final String SQL_UPD_USER_INFO
            = "UPDATE user_info SET first_name = ?, last_name = ?, "
            + "registration_date = ?, icon_reference = ? WHERE user_id = ?";

    //language=SQL
    private static final String SQL_FIND_ALL_USER_INFO
            = "SELECT * FROM user_info";

    //language=SQL
    private static final String SQL_FIND_USER_INFO_BY_ID
            = "SELECT * FROM user_info WHERE user_id = ?";


    public UserInfoDaoImpl(final Connection connectionNew) {
        super(connectionNew);
    }

    /**
     * This method adds a new model in a table.
     *
     * @param model model to add
     * @return true if model has been added, otherwise false
     */
    @Override
    public Long create(UserInfo model) throws ProviderException {
        try (PreparedStatement statement
                     = this.connection.prepareStatement(
                SQL_ADD_USER_TO_USER_INFO)) {

            statement.setLong(1, model.getId());
            statement.setString(2, model.getFirstName());
            statement.setString(3, model.getLastName());
            statement.setDate(4, Date
                    .valueOf(model.getRegistrationDate()));
            statement.setString(5, model.getIconReference());

            statement.executeUpdate();

            log.trace("Added data to user_info table, key: " + model.getId());
            return model.getId();

        } catch (SQLException e) {
            log.error("Cannot add data to user_info table");
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
    public void update(UserInfo model) throws ProviderException {
        try (PreparedStatement statement
                     = this.connection.prepareStatement(
                SQL_UPD_USER_INFO)) {

            statement.setString(1, model.getFirstName());
            statement.setString(2, model.getLastName());
            statement.setDate(3, Date
                    .valueOf(model.getRegistrationDate()));
            statement.setString(4, model.getIconReference());

            statement.setLong(5, model.getId());

            statement.executeUpdate();

            log.trace("Added data to user_info table, key: " + model.getId());

        } catch (SQLException e) {
            log.error("Cannot add data to user_info table");
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
    public List<UserInfo> findAll() throws ProviderException {
        List<UserInfo> usersInfo = new LinkedList<>();

        try (Statement statement = this.connection.createStatement()) {

            try (ResultSet rs = statement.executeQuery(SQL_FIND_ALL_USER_INFO)) {
                while (rs.next()) {
                    usersInfo.add(userInfoFromResultSet(rs));
                }
            }
            return usersInfo;
        } catch (SQLException e) {
            throw new ProviderException(e);
        }
    }

    /**
     * This method returns all models from table in preset range
     * (for page representation).
     *
     * @param startValue start value
     * @param endValue   end value
     * @return a list of all models from table in range
     */
    @Override
    public List<UserInfo> findAllInRange(Long startValue, Long endValue) {
        return Collections.emptyList();
    }

    /**
     * This method returns a model from table by its id.
     *
     * @param id id to find model
     * @return present optional if model has been found,
     * otherwise empty optional.
     */
    @Override
    public Optional<UserInfo> findById(Long id) throws ProviderException {
        Optional<UserInfo> optionalUserInfo;

        try (PreparedStatement statement = this.connection
                .prepareStatement(SQL_FIND_USER_INFO_BY_ID)) {

            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    optionalUserInfo = Optional.of(userInfoFromResultSet(rs));
                } else {
                    optionalUserInfo = Optional.empty();
                }
            }
            return optionalUserInfo;

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
    public boolean removeById(Long id) {
        return false;
    }

    /**
     * This method deletes a model from table.
     *
     * @param model model to delete
     * @return true if model has been deleted, otherwise false
     */
    @Override
    public boolean remove(UserInfo model) {
        return false;
    }

}
