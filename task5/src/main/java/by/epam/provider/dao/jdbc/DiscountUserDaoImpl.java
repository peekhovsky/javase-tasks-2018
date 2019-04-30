package by.epam.provider.dao.jdbc;

import by.epam.provider.dao.DiscountUserDao;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.Discount;
import by.epam.provider.model.DiscountUser;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @author Rostislav Pekhovsky
 * @version 0.1
 */
@NoArgsConstructor
@Log4j2
public final class DiscountUserDaoImpl extends AbstractDaoImpl implements DiscountUserDao {

    //language=SQL
    private static final String SQL_ADD_DISC_USER
            = "INSERT INTO discount_user (user_id, discount_id) "
            + "VALUES (?, ?)";

    //language=SQL
    private static final String SQL_FIND_ALL_DISC_USER
            = "SELECT * FROM discount_user";

    //language=SQL
    private static final String SQL_FIND_ALL_DISC_USER_BY_USER_ID
            = "SELECT * FROM discount_user WHERE user_id = ?";
    //language=SQL
    private static final String SQL_FIND_ALL_DISC_BY_USER_ID
            = "SELECT discount.id, discount.tariff_id, discount.start_date, "
            + "discount.finish_date, discount.percent FROM discount_user "
            + "LEFT JOIN discount ON discount_user.discount_id = discount.id "
            + "WHERE user_id = ?";
    //language=SQL
    private static final String SQL_REMOVE_BY_IDS
            = "DELETE FROM discount_user WHERE user_id = ? AND discount_id = ?";

    //language=SQL
    private static final String SQL_FIND_ALL_DISC_USER_LIMIT
            = "SELECT * FROM discount_user LIMIT ?, ?";

    //language=SQL
    private static final String SQL_DISC_USER_ROW_NUMBER
            = "SELECT COUNT(*) FROM discount_user";


    public DiscountUserDaoImpl(Connection connection) {
        super(connection);
    }

    /**
     * This method adds a new model in a table.
     *
     * @param model model to add
     * @return true if model has been added, otherwise false
     */
    @Override
    public Long create(DiscountUser model) throws ProviderException {
        try (PreparedStatement statement = this.connection.prepareStatement(
                SQL_ADD_DISC_USER)) {
            createStatementFromModel(model, statement);
            statement.execute();
            return model.getUserId();
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
    public void update(DiscountUser model) throws ProviderException {
        throw new ProviderException(new UnsupportedOperationException(), 501);
    }

    /**
     * This method returns all models from table.
     *
     * @return a list of all models from table
     */
    @Override
    public List<DiscountUser> findAll() throws ProviderException {
        List<DiscountUser> users = new ArrayList<>(size().intValue());
        try (Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(SQL_FIND_ALL_DISC_USER)) {
                while (rs.next()) {
                    DiscountUser discountUser = DiscountUser.builder()
                            .userId(rs.getLong("user_id"))
                            .discountId(rs.getLong("discount_id"))
                            .build();
                    users.add(discountUser);
                }
            }
            return users;
        } catch (SQLException e) {
            throw new ProviderException(e, 500);
        }
    }

    @Override
    public List<DiscountUser> findAllByUserId(final long userId)
            throws ProviderException {

        List<DiscountUser> users = new ArrayList<>(size().intValue());

        try (PreparedStatement statement = connection.prepareStatement(
                SQL_FIND_ALL_DISC_USER_BY_USER_ID)) {
            statement.setLong(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    DiscountUser discountUser = DiscountUser.builder()
                            .userId(rs.getLong("user_id"))
                            .discountId(rs.getLong("discount_id"))
                            .build();
                    users.add(discountUser);
                }
            }
            return users;
        } catch (SQLException e) {
            throw new ProviderException(e, 500);
        }
    }

    @Override
    public List<Discount> findAllByUserIdDiscount(final long userId)
            throws ProviderException {

        List<Discount> users = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(
                SQL_FIND_ALL_DISC_BY_USER_ID)) {
            statement.setLong(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Discount discount = Discount.builder()
                            .id(rs.getLong("id"))
                            .tariffId(rs.getLong("tariff_id"))
                            .startDate(rs.getDate("start_date")
                                    .toLocalDate())
                            .finishDate(rs.getDate("finish_date")
                                    .toLocalDate())
                            .percent(rs.getFloat("percent"))
                            .build();
                    users.add(discount);
                }
            }
            return users;
        } catch (SQLException e) {
            throw new ProviderException(e, 500);
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
    public List<DiscountUser> findAllInRange(Long startValue, Long limit)
            throws ProviderException {
        throw new ProviderException(new UnsupportedOperationException(), 501);
    }

    public boolean f(long userId, long discountId) throws ProviderException {

        List<DiscountUser> users = new ArrayList<>(size().intValue());

        try (PreparedStatement statement = connection.prepareStatement(
                SQL_FIND_ALL_DISC_USER_BY_USER_ID)) {
            statement.setLong(1, userId);
            statement.setLong(2, discountId);

        } catch (SQLException e) {
            throw new ProviderException(e, 500);
        }

        return false;
    }

    /**
     * This method returns a model from table by its id.
     *
     * @param id id to find model
     * @return present optional if model has been found,
     * otherwise empty optional.
     */
    @Override
    public Optional<DiscountUser> findById(final Long id) throws ProviderException {
        throw new ProviderException(new UnsupportedOperationException(), 501);
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

    @Override
    public void removeByUserId(Long userId, Long discountId)
            throws ProviderException {

        try (PreparedStatement statement = connection.prepareStatement(
                SQL_REMOVE_BY_IDS)) {
            statement.setLong(1, userId);
            statement.setLong(2, discountId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new ProviderException(e, 500);
        }
    }


    /**
     * This method deletes a model from table.
     *
     * @param model model to delete
     * @return true if model has been deleted, otherwise false
     */
    @Override
    public boolean remove(DiscountUser model) throws ProviderException {
        throw new ProviderException(new UnsupportedOperationException(), 501);
    }


    @Override
    @SuppressWarnings("Duplicates")
    public Long size() throws ProviderException {
        return DaoRegular.size(SQL_DISC_USER_ROW_NUMBER, this.connection);
    }


    private void createStatementFromModel(DiscountUser model,
                                          PreparedStatement statement)
            throws SQLException {
        log.info(model);
        statement.setLong(1, model.getUserId());
        statement.setLong(2, model.getDiscountId());
    }

}
