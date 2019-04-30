package by.epam.provider.command;

import by.epam.provider.command.ServletCommand;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.factory.ServiceFactoryImpl;
import by.epam.provider.model.User;
import by.epam.provider.model.form.UserForm;
import by.epam.provider.security.UserType;
import by.epam.provider.service.UserService;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Log4j2
public class AdminParamCommand extends ServletCommand {

    public AdminParamCommand(final ServiceFactoryImpl serviceFactoryNew) {
        super(serviceFactoryNew);
        this.enabledUserTypes.add(UserType.ADMIN);
    }

    @Override
    public void executeGet(final HttpServletRequest req,
                           final HttpServletResponse resp) throws ProviderException {
        try {
            req.getRequestDispatcher("/jsp/date.jsp")
                    .forward(req, resp);
        } catch (ServletException | IOException e) {
            log.error(e.getMessage());
            throw new ProviderException(e);
        }
    }

    @Override
    public void executePost(final HttpServletRequest req,
                            final HttpServletResponse resp) throws ProviderException {
        try {

            String action = req.getParameter("action");

            UserService service = this.serviceFactory.findService(UserService.class);

            switch (action.toUpperCase()) {
                case "CHARGE":
                    service.chargeUsers();
                    break;
                case "BLOCK":
                    service.blockUsersWithNegativeBalance();
                    break;

                default:
            }

            resp.sendRedirect(req.getContextPath()
                    + "/date?message=Users have been charged. ");
        } catch (IOException e) {
            throw new ProviderException(e);
        }
    }

}
