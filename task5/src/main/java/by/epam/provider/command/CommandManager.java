package by.epam.provider.command;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.factory.ServiceFactoryImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
@Log4j2
public class CommandManager {

    @Getter
    private ServiceFactoryImpl serviceFactory;

    private static final Map<String, Class<? extends ServletCommand>> COMMANDS
            = new HashMap<>();

    static {
        COMMANDS.put("sign_up", SignUpCommand.class);
        COMMANDS.put("sign_in", SignInCommand.class);
        COMMANDS.put("log_out", LogOutCommand.class);
        COMMANDS.put("user_info", UserInfoCommand.class);
        COMMANDS.put("tariffs", TariffInfoCommand.class);
        COMMANDS.put("add_tariff", AddTariffCommand.class);
        COMMANDS.put("set_tariff", SetTariffCommand.class);
        COMMANDS.put("error", ErrorCommand.class);
        COMMANDS.put("main", MainCommand.class);
        COMMANDS.put("pay", PayCommand.class);
        COMMANDS.put("date", AdminParamCommand.class);
        COMMANDS.put("bills", BillsCommand.class);
        COMMANDS.put("discounts", DiscountsCommand.class);
        COMMANDS.put("add_discount", AddDiscountCommand.class);
        COMMANDS.put("users_list", UserListCommand.class);
        COMMANDS.put("ban_user", BanUserCommand.class);
        COMMANDS.put("block_tariff", BlockTariffCommand.class);
    }

    public CommandManager(final ServiceFactoryImpl serviceFactoryNew) {
        this.serviceFactory = serviceFactoryNew;
    }

    @NonNull
    public void execute(final CommandMethod method,
                        final HttpServletRequest req,
                        final HttpServletResponse resp)
            throws ProviderException {

        String query = (String) req.getAttribute("query");

        Class<? extends ServletCommand> commandClass = COMMANDS.get(query);

        if (commandClass != null) {
            try {
                ServletCommand command = commandClass
                        .getDeclaredConstructor(ServiceFactoryImpl.class)
                        .newInstance(serviceFactory);

                command.checkUserType(req);

                switch (method) {
                    case GET:
                        command.executeGet(req, resp);
                        break;

                    case POST:
                        command.executePost(req, resp);
                        break;

                    case PUT:
                        command.executePut(req, resp);
                        break;

                    case DELETE:
                        command.executeDelete(req, resp);
                        break;

                    default:
                        throw new ProviderException("Unsupported method", 405);
                }

            } catch (InstantiationException | IllegalAccessException
                    | NoSuchMethodException | InvocationTargetException e) {
                log.error(e.getMessage());
                throw new ProviderException(e);
            }
        } else {
            throw new ProviderException("Cannot find an appropriate command "
                    + "class for this query.", 501);
        }
    }
}
