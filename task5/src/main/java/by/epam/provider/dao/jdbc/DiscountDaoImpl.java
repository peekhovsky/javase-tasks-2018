package by.epam.provider.dao.jdbc;

import by.epam.provider.dao.DiscountDao;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.Discount;
import by.epam.provider.model.dto.DiscountDto;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.provider.dao.jdbc.DaoRegular.receiveKeyAfterExecute;


/**
 * @author Rostislav Pekhovsky
 * @version 0.1
 */
@NoArgsConstructor
@Log4j2
public final class DiscountDaoImpl extends AbstractDaoImpl implements DiscountDao {

    //language=SQL
    private static final String SQL_ADD_DISCOUNT_TO_DISCOUNT
            = "INSERT INTO discount (tariff_id, start_date, "
            + "finish_date, percent)"
            + "VALUES (?, ?, ?, ?)";

    //language=SQL
    private static final String SQL_UPT_DISCOUNT
            = "UPDATE discount SET tariff_id = ?, start_date = ?, "
            + "finish_date = ?, percent = ? WHERE id = ?";

    //language=SQL
    private static final String SQL_FIND_ALL_DTO
            = "SELECT discount.id, tariff_id, start_date, finish_date, percent, "
            + " tariff.name FROM discount "
            + "LEFT JOIN tariff ON discount.tariff_id = tariff.id";

    //language=SQL
    private static final String SQL_FIND_BY_ID
            = "SELECT * FROM discount WHERE id = ?";

    //language=SQL
    private static final String SQL_DISCOUNT_ROW_NUMBER
            = "SELECT COUNT(*) FROM discount";

    public DiscountDaoImpl(final Connection connectionNew) {
        super(connectionNew);
    }


    /**
     * This method adds a new model in a table.
     *
     * @param model model to add
     * @return true if model has been added, otherwise false
     */
    @Override
    public Long create(Discount model) throws ProviderException {
        try (PreparedStatement statement
                     = this.connection.prepareStatement(
                SQL_ADD_DISCOUNT_TO_DISCOUNT,
                Statement.RETURN_GENERATED_KEYS)) {

            statementFromModel(model, statement);
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


    /**
     * This method updates a model in a table (by id from model value).
     *
     * @param model model in a table to update
     * @return true if model has been updated, otherwise false
     */
    @Override
    public void update(Discount model) throws ProviderException {
        try (PreparedStatement statement
                     = this.connection.prepareStatement(
                SQL_UPT_DISCOUNT)) {

            statementFromModel(model, statement);
            statement.setLong(5, model.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            log.warn("Cannot add data to user table");
            log.warn(e.getMessage());
            throw new ProviderException(e);
        }
    }

    /**
     * This method returns all models from table.
     *
     * @return a list of all models from table
     */
    @Override
    public List<DiscountDto> findAllDto() throws ProviderException {
        try (PreparedStatement statement = this.connection.prepareStatement(
                SQL_FIND_ALL_DTO)) {

            List<DiscountDto> discounts = new ArrayList<>(size().intValue());

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Discount discount = discountFromResultSet(rs);
                    DiscountDto discountDto = new DiscountDto(discount);
                    discountDto.setTariffName(rs.getString("name"));
                    discounts.add(discountDto);
                }
            }
            return discounts;

        } catch (SQLException e) {
            log.warn("Cannot add data to user table");
            log.warn(e.getMessage());
            throw new ProviderException(e);
        }
    }

    /**
     * This method returns all models from table.
     *
     * @return a list of all models from table
     */
    @Override
    public List<Discount> findAll() throws ProviderException {
        try (PreparedStatement statement = this.connection.prepareStatement(
                SQL_FIND_ALL_DTO)) {

            List<Discount> discounts = new ArrayList<>(size().intValue());

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Discount discount = discountFromResultSet(rs);
                    discounts.add(discount);
                }
            }
            return discounts;

        } catch (SQLException e) {
            log.warn("Cannot add data to user table");
            log.warn(e.getMessage());
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
    public List<Discount> findAllInRange(Long startValue, Long endValue) throws ProviderException {
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
    public Optional<Discount> findById(Long id) throws ProviderException {
        try (PreparedStatement statement = this.connection.prepareStatement(
                SQL_FIND_BY_ID)) {
            statement.setLong(1, id);
            Optional<Discount> discountOptional;

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    discountOptional = Optional.of(discountFromResultSet(rs));
                } else {
                    discountOptional = Optional.empty();
                }
            }
            return discountOptional;

        } catch (SQLException e) {
            log.warn("Cannot add data to user table");
            log.warn(e.getMessage());
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
        return false;
    }

    /**
     * This method deletes a model from table.
     *
     * @param model model to delete
     * @return true if model has been deleted, otherwise false
     */
    @Override
    public boolean remove(Discount model) throws ProviderException {
        return false;
    }

    @Override
    public Long size() throws ProviderException {
        return DaoRegular.size(SQL_DISCOUNT_ROW_NUMBER, this.connection);
    }

    private void statementFromModel(Discount model, PreparedStatement statement) throws SQLException {
        statement.setLong(1, model.getTariffId());
        statement.setDate(2, Date.valueOf(model.getStartDate()));
        statement.setDate(3, Date.valueOf(model.getFinishDate()));
        statement.setFloat(4, model.getPercent());
    }

    private Discount discountFromResultSet(final ResultSet rs) throws SQLException {
        return Discount.builder()
                .id(rs.getLong("id"))
                .tariffId(rs.getLong("tariff_id"))
                .startDate(rs.getDate("start_date").toLocalDate())
                .finishDate(rs.getDate("finish_date").toLocalDate())
                .percent(rs.getFloat("percent"))
                .build();
    }
}

