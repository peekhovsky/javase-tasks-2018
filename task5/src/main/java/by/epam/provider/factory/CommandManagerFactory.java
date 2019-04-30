package by.epam.provider.factory;

import by.epam.provider.command.CommandManager;
import by.epam.provider.transaction.ConnectionPool;
import lombok.extern.log4j.Log4j2;

@Log4j2
public final class CommandManagerFactory {

    private CommandManagerFactory() {
    }

    public static CommandManager commandManager() {
        ServiceFactoryImpl serviceFactory
                = new ServiceFactoryImpl(ConnectionPool.getInstance());
        return new CommandManager(serviceFactory);
    }
}
