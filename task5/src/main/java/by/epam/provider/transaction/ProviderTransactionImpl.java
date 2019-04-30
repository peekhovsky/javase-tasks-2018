package by.epam.provider.transaction;

import by.epam.provider.dao.*;
import by.epam.provider.dao.jdbc.*;
import by.epam.provider.exception.ProviderException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ProviderTransactionImpl extends ProviderTransaction {

    private static final
    Map<Class<? extends CrudDao>, Class<? extends AbstractDaoImpl>> daos
            = new ConcurrentHashMap<>();

    static {
        daos.put(UserDao.class, UserDaoImpl.class);
        daos.put(UserInfoDao.class, UserInfoDaoImpl.class);
        daos.put(ClientUserDao.class, ClientUserDaoImpl.class);
        daos.put(TariffDao.class, TariffDaoImpl.class);
        daos.put(BillHistoryDao.class, BillHistoryDaoImpl.class);
        daos.put(DiscountDao.class, DiscountDaoImpl.class);
        daos.put(DiscountUserDao.class, DiscountUserDaoImpl.class);
    }

    public ProviderTransactionImpl(final Connection connectionNew) {
        this.connection = connectionNew;
    }

    @SuppressWarnings("unchecked")
    public <T extends CrudDao<?, ?>> T createDao(Class<T> dao)
            throws ProviderException {
        try {
            Class<? extends AbstractDaoImpl> daoClass = daos.get(dao);
            AbstractDaoImpl daoImpl = daoClass.newInstance();
            daoImpl.setConnection(connection);
            return (T) daoImpl;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ProviderException(e);
        } catch (NullPointerException e) {
            throw new ProviderException(
                    "Cannot find an appropriate implementation.");
        }
    }

    public void commit() throws ProviderException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new ProviderException(e);
        }
    }

    public void rollback() throws ProviderException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new ProviderException(e);
        }
    }
}
