package by.epam.provider.dao.jdbc;

import by.epam.provider.dao.BillHistoryDao;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.Bill;
import by.epam.provider.model.dto.BillDto;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.*;

import static by.epam.provider.dao.jdbc.DaoRegular.receiveKeyAfterExecute;


/**
 * @author Rostislav Pekhovsky
 * @version 0.1
 */
@NoArgsConstructor
@Log4j2
public final class BillHistoryDaoImpl extends AbstractDaoImpl implements BillHistoryDao {


    //language=SQL
    private static final String SQL_ADD_BILL_TO_BILL_HISTORY
            = "INSERT INTO bill_history (tariff_id, user_id, "
            + "sum, date)"
            + "VALUES (?, ?, ?, ?)";

    //language=SQL
    private static final String SQL_FIND_ALL_BY_USER_ID
            = "SELECT bill_history.id, tariff_id, sum, date, user_id, "
            + " tariff.name FROM bill_history "
            + "LEFT JOIN tariff ON bill_history.tariff_id = tariff.id "
            + "WHERE bill_history.user_id = ?";

    //language=SQL
    private static final String SQL_FIND_ALL_BILL_HISTORY
            = "SELECT * FROM bill_history";

    //language=SQL
    private static final String SQL_FIND_BILLS_INFO_BY_ID
            = "SELECT * FROM bill_history WHERE id = ?";


    public BillHistoryDaoImpl(final Connection connectionNew) {
        super(connectionNew);
    }

    /**
     * This method adds a new model in a table.
     *
     * @param model model to add
     * @return true if model has been added, otherwise false
     */
    @Override
    public Long create(Bill model) throws ProviderException {
        try (PreparedStatement statement
                     = this.connection.prepareStatement(
                SQL_ADD_BILL_TO_BILL_HISTORY,
                Statement.RETURN_GENERATED_KEYS)) {

            log.info(model);
            statement.setLong(1, model.getTariffId());
            statement.setLong(2, model.getUserId());
            statement.setLong(3, model.getSum());
            statement.setTimestamp(4, Timestamp.valueOf(model.getDate()));
            statement.executeUpdate();
            long billKey = receiveKeyAfterExecute(statement);
            log.trace("Added data to bill table, key: " + billKey);
            return billKey;

        } catch (SQLException e) {
            log.warn("Cannot add data to user table");
            log.warn(e.getMessage());
            throw new ProviderException(e);
        }
    }

    @Override
    public void createAll(final List<Bill> bills) throws ProviderException {
        for (Bill bill : bills) {
            create(bill);
        }
    }

    /**
     * This method updates a model in a table (by id from model value).
     *
     * @param model model in a table to update
     * @return true if model has been updated, otherwise false
     */
    @Override
    public void update(Bill model) throws ProviderException {
        throw new ProviderException(new UnsupportedOperationException(), 501);
    }

    /**
     * This method returns all models from table.
     *
     * @return a list of all models from table
     */
    @Override
    public List<Bill> findAll() throws ProviderException {
        throw new ProviderException(new UnsupportedOperationException(), 501);
    }


    @Override
    public List<BillDto> findAllByUserIdDto(final long id) throws ProviderException {
        try (PreparedStatement statement = this.connection
                .prepareStatement(SQL_FIND_ALL_BY_USER_ID)) {

            statement.setLong(1, id);

            List<BillDto> bills = new LinkedList<>();

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Bill bill = billFromResultSet(rs);
                    BillDto billDto = new BillDto(bill);
                    billDto.setTariffName(rs.getString("name"));
                    bills.add(billDto);
                }
            }
            return bills;
        } catch (SQLException e) {
            log.warn("Cannot make a query to bill history table. ");
            log.warn(e.getMessage());
            throw new ProviderException(e);
        }
    }


    @Override
    public List<Bill> findAllByUserId(final long id) throws ProviderException {
        try (PreparedStatement statement = this.connection.prepareStatement(
                SQL_FIND_ALL_BY_USER_ID, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, id);

            List<Bill> bills = new LinkedList<>();

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Bill bill = billFromResultSet(rs);
                    bills.add(bill);
                }
            }
            return bills;
        } catch (SQLException e) {
            log.warn("Cannot make a query to bill history table. ");
            log.warn(e.getMessage());
            throw new ProviderException(e);
        }
    }

    @Override
    public List<Bill> findAllByUserIdInRange(long id, int pageNum) throws ProviderException {
        throw new ProviderException(new UnsupportedOperationException(), 501);
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
    public List<Bill> findAllInRange(Long startValue, Long endValue) throws ProviderException {
        throw new ProviderException(new UnsupportedOperationException(), 501);
    }

    /**
     * This method returns a model from table by its id.
     *
     * @param id id to find model
     * @return present optional if model has been found,
     * otherwise empty optional.
     */
    @Override
    public Optional<Bill> findById(Long id) throws ProviderException {
        return Optional.empty();
    }

    /**
     * This method deletes a model from table by its id.
     *
     * @param id id to delete model
     * @return true if model has been deleted, otherwise false
     */
    @Override
    public boolean removeById(Long id) throws ProviderException {
        return false;
    }

    /**
     * This method deletes a model from table.
     *
     * @param model model to delete
     * @return true if model has been deleted, otherwise false
     */
    @Override
    public boolean remove(Bill model) throws ProviderException {
        throw new ProviderException(new UnsupportedOperationException(), 501);
    }

    private Bill billFromResultSet(final ResultSet rs) throws SQLException {
        return Bill.builder()
                .id(rs.getLong("id"))
                .tariffId(rs.getLong("tariff_id"))
                .userId(rs.getLong("user_id"))
                .sum(rs.getLong("sum"))
                .date(rs.getTimestamp("date").toLocalDateTime())
                .build();
    }
}
