package by.epam.provider.command;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.factory.ServiceFactoryImpl;
import by.epam.provider.security.UserType;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Log4j2
public class ErrorCommand extends ServletCommand {

    public ErrorCommand(ServiceFactoryImpl serviceFactoryNew) {
        super(serviceFactoryNew);
        this.enabledUserTypes.add(UserType.GUEST);
        this.enabledUserTypes.add(UserType.CLIENT);
        this.enabledUserTypes.add(UserType.ADMIN);
    }

    @Override
    public void executeGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ProviderException {
        try {
            int errorCode = (int) req.getAttribute("errorCode");
            if (errorCode >= 500) {
                log.error("Internal server error. Error code: " + errorCode);
            } else {
                log.debug("External mistake. Error code: " + errorCode);
            }
            req.getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
        } catch (IOException | ServletException e) {
            throw new ProviderException(e);
        }
    }
}
