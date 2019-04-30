package by.epam.provider.command;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.factory.ServiceFactoryImpl;
import by.epam.provider.model.User;
import by.epam.provider.security.UserType;
import by.epam.provider.service.UserService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Log4j2
public class BanUserCommand extends ServletCommand {


    public BanUserCommand(final ServiceFactoryImpl serviceFactoryNew) {
        super(serviceFactoryNew);
        this.enabledUserTypes.add(UserType.ADMIN);
    }

    @Override
    public void executeGet(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        try {
            UserService userService
                    = this.serviceFactory.findService(UserService.class);

            long id = Long.parseLong(req.getParameter("id"));
            String action = req.getParameter("action");

            switch (action.toUpperCase()) {
                case "BAN":
                    userService.banUserById(id);
                    break;
                case "UNBAN":
                    userService.unbanUserById(id);
                    break;
                default:
                    throw new ProviderException("Bad request", 400);
            }

            resp.sendRedirect(req.getContextPath() + "/users_list?page_num=0");

        } catch (IOException e) {
            throw new ProviderException(e, 500);
        } catch (NumberFormatException | NullPointerException e) {
            throw new ProviderException("Bad request. ", 400);
        }
    }
}
