package by.epam.provider.command;

import by.epam.provider.command.ServletCommand;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.factory.ServiceFactoryImpl;
import by.epam.provider.security.UserType;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class LogOutCommand extends ServletCommand {

    public LogOutCommand(ServiceFactoryImpl serviceFactoryNew) {
        super(serviceFactoryNew);
        this.enabledUserTypes.add(UserType.ADMIN);
        this.enabledUserTypes.add(UserType.CLIENT);
    }

    @Override
    public void executeGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ProviderException {
        try {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath());
        } catch (IOException e) {
            throw new ProviderException(e, 401);
        }
    }
}
