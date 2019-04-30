package by.epam.provider.dao.jdbc;

import by.epam.provider.dao.TariffDao;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.Tariff;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static by.epam.provider.dao.jdbc.DaoRegular.receiveKeyAfterExecute;


/**
 * @author Rostislav Pekhovsky
 * @version 0.1
 */
@NoArgsConstructor
@Log4j2
public final class TariffDaoImpl extends AbstractDaoImpl implements TariffDao {

    //language=SQL
    private static final String SQL_ADD_TARIFF_TO_TARIFF
            = "INSERT INTO tariff (name, description, free_mb_per_month,"
            + "speed, speed_over_traffic, day_bill, status)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    //language=SQL
    private static final String SQL_UPD_TARIFF
            = "UPDATE tariff SET name = ?, description = ?, free_mb_per_month = ?,"
            + "speed = ?, speed_over_traffic = ?, day_bill = ?, status = ?"
            + "WHERE id = ?";

    //language=SQL
    private static final String SQL_FIND_ALL_TARIFF
            = "SELECT * FROM tariff WHERE status <> 'DELETED'";

    //language=SQL
    private static final String SQL_FIND_ALL_ACTIVE_TARIFF
            = "SELECT * FROM tariff WHERE status = 'ACTIVE'";


    //language=SQL
    private static final String SQL_FIND_TARIFF_BY_ID
            = "SELECT * FROM tariff WHERE id = ?";

    //language=SQL
    private static final String SQL_REMOVE_TARIFF_BY_ID
            = "DELETE FROM tariff WHERE id = ?";

    //language=SQL
    private static final String SQL_TARIFF_ROW_NUMBER
            = "SELECT COUNT(*) FROM tariff";

    public TariffDaoImpl(final Connection connectionNew) {
        super(connectionNew);
    }

    /**
     * This method adds a new model in a table.
     *
     * @param model model to add
     * @return true if model has been added, otherwise false
     */
    @Override
    public Long create(Tariff model) throws ProviderException {
        try (PreparedStatement statement
                     = this.connection.prepareStatement(
                SQL_ADD_TARIFF_TO_TARIFF,
                Statement.RETURN_GENERATED_KEYS)
        ) {

            createStatementFromModel(model, statement);
            statement.execute();
            long key = receiveKeyAfterExecute(statement);

            log.trace("Added data to tariff table, key: " + key);
            return key;

        } catch (SQLException e) {
            log.error("Cannot add data to tariff table");
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
    public void update(Tariff model) throws ProviderException {
        try (PreparedStatement statement
                     = this.connection.prepareStatement(
                SQL_UPD_TARIFF)) {
            createStatementFromModel(model, statement);
            statement.setLong(8, model.getId());
            statement.execute();
            log.trace("Update data in tariff table, key: " + model.getId());
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
    public List<Tariff> findAll() throws ProviderException {
        return findAll(SQL_FIND_ALL_TARIFF);
    }


    /**
     * This method returns all models from table.
     *
     * @return a list of all models from table
     */
    @Override
    public List<Tariff> findAllActive() throws ProviderException {
        return findAll(SQL_FIND_ALL_ACTIVE_TARIFF);
    }


    public List<Tariff> findAll(final String query) throws ProviderException {
        List<Tariff> tariffs = new LinkedList<>();

        try (Statement statement = connection.createStatement()) {

            try (ResultSet rs = statement.executeQuery(query)) {
                while (rs.next()) {
                    tariffs.add(tariffFromResultSet(rs));
                }
            }
            return tariffs;
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
    public List<Tariff> findAllInRange(Long startValue, Long endValue) throws ProviderException {
        return null;
    }

    /**
     * This method returns a model from table by its id.
     *
     * @param id id to find model
     * @return present optional if model has been found,
     * otherwise empty optional.
     */
    @Override
    public Optional<Tariff> findById(Long id) throws ProviderException {
        Optional<Tariff> optionalTariff;

        try (PreparedStatement statement = this.connection
                .prepareStatement(SQL_FIND_TARIFF_BY_ID)) {

            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    optionalTariff = Optional.of(tariffFromResultSet(rs));
                } else {
                    optionalTariff = Optional.empty();
                }
            }
            return optionalTariff;

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
    public boolean remove(Tariff model) throws ProviderException {
        throw new ProviderException(new UnsupportedOperationException(), 501);
    }

    @Override
    public Long size() throws ProviderException {
        return DaoRegular.size(SQL_TARIFF_ROW_NUMBER, this.connection);
    }

    private void createStatementFromModel(Tariff model, PreparedStatement statement) throws SQLException {
        statement.setString(1, model.getName());
        statement.setString(2, model.getDescription());
        statement.setInt(3, model.getFreeMbPerMonth());
        statement.setInt(4, model.getSpeed());
        statement.setInt(5, model.getSpeedOverTraffic());
        statement.setLong(6, model.getDayBill());
        statement.setString(7, model.getStatus().name());
    }

    static Tariff tariffFromResultSet(ResultSet rs) throws SQLException {
        return Tariff.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .freeMbPerMonth(rs.getInt("free_mb_per_month"))
                .speed(rs.getInt("speed"))
                .speedOverTraffic(rs.getInt("speed_over_traffic"))
                .dayBill(rs.getInt("day_bill"))
                .status(Tariff.Status.valueOf(rs.getString("status")))
                .build();
    }
}
