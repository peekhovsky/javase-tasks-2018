package by.epam.provider.command;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.factory.ServiceFactoryImpl;
import by.epam.provider.model.ClientUser;
import by.epam.provider.model.User;
import by.epam.provider.security.UserType;
import by.epam.provider.service.ClientUserService;
import by.epam.provider.service.UserService;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


@Log4j2
public class MainCommand extends ServletCommand {

    public MainCommand(final ServiceFactoryImpl serviceFactoryNew) {
        super(serviceFactoryNew);
    }

    @Override
    public void executeGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ProviderException {
        try {
            UserType userType = (UserType) req.getSession().getAttribute("user_type");

            switch (userType) {
                case CLIENT:
                    executeGetClient(req, resp);
                    break;

                case ADMIN:
                    executeGetAdmin(req, resp);
                    break;

                case GUEST:
                    executeGetGuest(req, resp);
                    break;

                default:
                    throw new ProviderException("Undefined type of user", 501);
            }

        } catch (ServletException | IOException e) {
            throw new ProviderException(e);
        }
    }

    private void executeGetGuest(final HttpServletRequest req,
                                 final HttpServletResponse resp)
            throws IOException {
        resp.sendRedirect(req.getRequestURI() + "sign_in");
    }

    private void executeGetClient(final HttpServletRequest req,
                                  final HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getRequestURI() + "user_info");
    }

    private void executeGetAdmin(final HttpServletRequest req,
                                 final HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getRequestURI() + "client");
    }
}
