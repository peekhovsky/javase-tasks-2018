package by.epam.provider.factory;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.transaction.ConnectionPool;
import by.epam.provider.service.*;
import by.epam.provider.transaction.ProviderTransactionImpl;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
public class ServiceFactoryImpl implements ServiceFactory {

    private ConnectionPool connectionPool;

    private static final
    Map<Class<? extends Service>, Class<? extends ServiceImpl>> SERVICE_IMPLS
            = new ConcurrentHashMap<>();

    static {
        SERVICE_IMPLS.put(UserService.class, UserServiceImpl.class);
        SERVICE_IMPLS.put(TariffService.class, TariffServiceImpl.class);
        SERVICE_IMPLS.put(BillHistoryService.class, BillHistoryServiceImpl.class);
        SERVICE_IMPLS.put(DiscountService.class, DiscountServiceImpl.class);
    }

    public ServiceFactoryImpl(final ConnectionPool connectionPoolNew) {
        this.connectionPool = connectionPoolNew;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Service> T findService(final Class<T> key)
            throws ProviderException {
        try {
            log.debug("Try to find " + key.getName());
            Class<? extends ServiceImpl> serviceClass = SERVICE_IMPLS.get(key);
            log.debug("Found: " + serviceClass.getName());
            ClassLoader classLoader = serviceClass.getClassLoader();
            Class<?>[] interfaces = {key};

            ServiceImpl service = serviceClass.newInstance();
            Connection connection = ConnectionPool.getInstance().pullConnection();
            connection.setAutoCommit(false);

            service.setTransaction(new ProviderTransactionImpl(connection));

            InvocationHandler invocationHandler = (proxy, method, args) -> {
                try {
                    Object result = method.invoke(service, args);
                    service.getTransaction().commit();
                    return result;
                } catch (ProviderException e) {
                    // e.printStackTrace();
                    service.getTransaction().rollback();
                    throw e;
                } catch (InvocationTargetException e) {
                    //  e.printStackTrace();
                    service.getTransaction().rollback();
                    throw new ProviderException(e, 500);
                } finally {
                    connectionPool.pushConnection(
                            service.getTransaction().getConnection());
                }
            };

            return (T) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);

        } catch (InstantiationException | IllegalAccessException | SQLException e) {
            throw new ProviderException(e, 500);
        } catch (NullPointerException e) {
            throw new ProviderException(
                    "Cannot find an appropriate implementation (1).", 501);
        }
    }
}
