package by.epam.provider.command;

import by.epam.provider.command.ServletCommand;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.factory.ServiceFactoryImpl;
import by.epam.provider.security.UserType;
import by.epam.provider.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class SetTariffCommand extends ServletCommand {


    public SetTariffCommand(ServiceFactoryImpl serviceFactoryNew) {
        super(serviceFactoryNew);
        this.enabledUserTypes.add(UserType.CLIENT);
    }

    @Override
    public void executePost(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        try {
            UserType userType = (UserType) req.getSession().getAttribute("user_type");

            switch (userType) {
                case ADMIN:
                    req.getRequestDispatcher("/jsp/error.jsp")
                            .forward(req, resp);
                    break;

                case GUEST:
                    resp.sendRedirect(req.getContextPath() + "/sign_in");
                    break;

                case CLIENT:
                    executePostClient(req, resp);
                    break;

                default:
                    throw new ProviderException("Illegal user type", 502);
            }

        } catch (ServletException | IOException e) {
            throw new ProviderException(e, 502);
        }
    }

    private void executePostClient(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        try {
            long tariffId = Long.parseLong(
                    Objects.requireNonNull(req.getParameter("id")));

            Long userId = Objects.requireNonNull((Long)
                    req.getSession().getAttribute("user_id"));

            UserService service = serviceFactory.findService(UserService.class);

            service.updateUserTariff(userId, tariffId);
            resp.sendRedirect(req.getContextPath() + "/?message="
                    + "Tariff has been set. ");

        } catch (NullPointerException e) {
            throw new ProviderException(e, 400);
        } catch (IOException e) {
            throw new ProviderException(e, 500);
        }
    }
}
