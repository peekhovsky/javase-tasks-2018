package by.epam.provider.dao.jdbc;

import by.epam.provider.dao.ClientUserDao;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.Bill;
import by.epam.provider.model.ClientUser;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;


/**
 * @author Rostislav Pekhovsky
 * @version 0.1
 */
@NoArgsConstructor
@Log4j2
public final class ClientUserDaoImpl extends AbstractDaoImpl implements ClientUserDao {

    //language=SQL
    private static final String SQL_ADD_USER_TO_CLIENT_USER
            = "INSERT INTO client_user (user_id, balance, status)"
            + "VALUES (?, ?, ?)";

    //language=SQL
    private static final String SQL_UPD_CLIENT_USER
            = "UPDATE client_user SET balance = ?, status = ?, tariff_id = ? "
            + "WHERE user_id=?";

    //language=SQL
    private static final String SQL_FIND_ALL_CLIENT_USER
            = "SELECT * FROM client_user";

    //language=SQL
    private static final String SQL_FIND_CLIENT_USER_BY_ID
            = "SELECT * FROM client_user WHERE user_id = ?";

    //language=SQL
    private static final String SQL_DELETE_CLIENT_USER_BY_ID
            = "DELETE FROM client_user WHERE user_id = ?";

    //language=SQL
    private static final String SQL_ADD_MONEY_TO_BALANCE
            = "UPDATE client_user SET balance = balance + ? WHERE user_id = ?";

    //language=SQL
    private static final String SQL_SELECT_CLIENT_USER_WITH_DAY_BILL
            = "SELECT user_id, balance, tariff_id, tariff.day_bill FROM client_user "
            + "LEFT JOIN tariff ON client_user.tariff_id = tariff.id";

    //language=SQL
    private static final String SQL_SELECT_CLIENT_USER_WITH_DAY_BILL_BY_ID
            = "SELECT user_id, balance, tariff_id, tariff.day_bill FROM client_user "
            + "LEFT JOIN tariff ON client_user.tariff_id = tariff.id "
            + "WHERE user_id = ?";

    //language=SQL
    private static final String SQL_SELECT_CLINET_USER_DISCOUNTS
            = "SELECT percent FROM discount_user "
            + "LEFT JOIN discount ON discount_user.discount_id = discount.id "
            + "WHERE user_id = ?";


    //language=SQL
    private static final String SQL_BLOCK_USERS_WITH_NEGATIVE_BALANCE
            = "UPDATE client_user SET status = 'BLOCKED'"
            + "WHERE balance < 0";


    //language=SQL
    private static final String SQL_CLIENT_ROW_NUMBER
            = "SELECT COUNT(*) FROM client_user";

    public ClientUserDaoImpl(final Connection connectionNew) {
        super(connectionNew);
    }

    /**
     * This method adds a new model in a table.
     *
     * @param model model to add
     * @return true if model has been added, otherwise false
     */
    @Override
    public Long create(ClientUser model) throws ProviderException {
        try (PreparedStatement statement
                     = this.connection.prepareStatement(
                SQL_ADD_USER_TO_CLIENT_USER,
                Statement.RETURN_GENERATED_KEYS)
        ) {

            statement.setLong(1, model.getId());
            statement.setLong(2, model.getBalance());
            statement.setString(3, model.getStatus().name());

            statement.executeUpdate();

            log.trace("Added data to client_user table, key: " + model.getId());
            return model.getId();

        } catch (SQLException e) {
            log.error("Cannot add data to client_user table");
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
    public void update(ClientUser model) throws ProviderException {
        try (PreparedStatement statement
                     = this.connection.prepareStatement(SQL_UPD_CLIENT_USER)) {

            statement.setLong(1, model.getBalance());
            statement.setString(2, model.getStatus().name());
            statement.setLong(3, model.getTariffId());
            statement.setLong(4, model.getId());
            statement.execute();

            log.trace("Update data in client_user table, key: " + model.getId());
        } catch (SQLException e) {
            log.error("Cannot add data to tariff table");
            log.error(e.getMessage());
            throw new ProviderException(e);
        }
    }

    @Override
    public void updateBlockUsersWithNegativeBalance() throws ProviderException {
        try (Statement statement
                     = this.connection.createStatement()) {
            statement.execute(SQL_BLOCK_USERS_WITH_NEGATIVE_BALANCE);
        } catch (SQLException e) {
            log.error("Cannot add data to tariff table");
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
    public List<ClientUser> findAll() throws ProviderException {
        List<ClientUser> clientUsers = new LinkedList<>();

        try (Statement statement = this.connection.createStatement()) {

            try (ResultSet rs = statement.executeQuery(SQL_FIND_ALL_CLIENT_USER)) {
                while (rs.next()) {
                    clientUsers.add(clientUserFromResultSet(rs));
                }
            }
            return clientUsers;
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
    public List<ClientUser> findAllInRange(final Long startValue,
                                           final Long endValue) {
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
    public Optional<ClientUser> findById(final Long id) throws ProviderException {
        Optional<ClientUser> optionalUserInfo;

        try (PreparedStatement statement = this.connection
                .prepareStatement(SQL_FIND_CLIENT_USER_BY_ID)) {

            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    optionalUserInfo = Optional
                            .of(clientUserFromResultSet(rs));
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
    public boolean removeById(final Long id) throws ProviderException {
        try (PreparedStatement statement = this.connection
                .prepareStatement(SQL_DELETE_CLIENT_USER_BY_ID)) {

            statement.setLong(1, id);
            int returnValue = statement.executeUpdate();

            return returnValue >= 0;

        } catch (SQLException e) {
            throw new ProviderException(e);
        }
    }

    /**
     * This method deletes a model from table.
     *
     * @param model model to delete
     * @return true if model has been deleted, otherwise false
     */
    @Override
    public boolean remove(ClientUser model) throws ProviderException {
        long id = model.getId();
        return removeById(id);
    }

    @Override
    public void addBalance(long amount, long userId) throws ProviderException {
        try (PreparedStatement statement = this.connection
                .prepareStatement(SQL_ADD_MONEY_TO_BALANCE)) {

            statement.setLong(1, amount);
            statement.setLong(2, userId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new ProviderException(e);
        }
    }

    @Override
    @SuppressWarnings("Duplicates")
    public List<Bill> chargeClientUsers() throws ProviderException {
        List<Bill> bills = new ArrayList<>(size().intValue());
        try (PreparedStatement statement = this.connection
                .prepareStatement(SQL_SELECT_CLIENT_USER_WITH_DAY_BILL)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    long userId = rs.getLong("user_id");
                    long tariffId = rs.getLong("tariff_id");
                    long dayBill = rs.getLong("day_bill");

                    float percent = findClientUserWholeDiscountById(userId);
                    dayBill *= percent;
                    log.debug("User id: " + userId + ", day bill: " + dayBill);
                    addBalance((-1) * dayBill, userId);

                    if (dayBill != 0) {
                        Bill bill = Bill.builder()
                                .userId(userId)
                                .tariffId(tariffId)
                                .sum(dayBill)
                                .date(LocalDateTime.now())
                                .build();
                        bills.add(bill);
                    }
                }
            }
            return bills;
        } catch (SQLException e) {
            throw new ProviderException(e);
        }
    }

    @Override
    @SuppressWarnings("Duplicates")
    public Optional<Bill> chargeClientUser(long id, float discount) throws ProviderException {
        try (PreparedStatement statement = this.connection
                .prepareStatement(SQL_SELECT_CLIENT_USER_WITH_DAY_BILL_BY_ID)) {

            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    long userId = rs.getLong("user_id");
                    long tariffId = rs.getLong("tariff_id");
                    long dayBill = rs.getLong("day_bill");
                    addBalance((long) ((-1) * dayBill * discount), userId);
                    Bill bill = Bill.builder()
                            .userId(userId)
                            .tariffId(tariffId)
                            .sum((long) (dayBill * discount))
                            .date(LocalDateTime.now())
                            .build();
                    return Optional.of(bill);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new ProviderException(e);
        }
    }


    @Override
    public Long size() throws ProviderException {
        return DaoRegular.size(SQL_CLIENT_ROW_NUMBER, this.connection);
    }

    private Float findClientUserWholeDiscountById(final long id)
            throws ProviderException {

        float wholePercent = 1F;

        try (PreparedStatement statement = this.connection
                .prepareStatement(SQL_SELECT_CLINET_USER_DISCOUNTS)) {

            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    float percent = rs.getFloat("percent");
                    wholePercent *= (1 - percent * 0.01);
                }
            }
            log.debug("Whole discount percent: " + wholePercent);
            return wholePercent;

        } catch (SQLException e) {
            throw new ProviderException(e);
        }
    }

    private ClientUser clientUserFromResultSet(final ResultSet rs)
            throws SQLException {
        return ClientUser.builder()
                .id(rs.getLong("user_id"))
                .balance(rs.getLong("balance"))
                .status(ClientUser.Status.valueOf(rs.getString("status")))
                .tariffId(rs.getLong("tariff_id"))
                .build();
    }


}
