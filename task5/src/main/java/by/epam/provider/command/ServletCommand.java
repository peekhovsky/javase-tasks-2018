package by.epam.provider.command;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.security.UserType;
import by.epam.provider.factory.ServiceFactoryImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;


@SuppressWarnings("WeakerAccess")
public abstract class ServletCommand {

    protected Set<UserType> enabledUserTypes = new HashSet<>();

    @Getter
    @Setter
    protected ServiceFactoryImpl serviceFactory;

    public ServletCommand(ServiceFactoryImpl serviceFactoryNew) {
        this.serviceFactory = serviceFactoryNew;
    }

    public void executeGet(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        throw new ProviderException(new UnsupportedOperationException(), 503);
    }

    public void executePost(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        throw new ProviderException(new UnsupportedOperationException(), 503);
    }

    public void executePut(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        throw new ProviderException(new UnsupportedOperationException(), 503);
    }

    public void executeDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        checkUserType(req);
        throw new ProviderException(new UnsupportedOperationException(), 503);
    }

    protected void checkUserType(HttpServletRequest req)
            throws ProviderException {
        UserType userType = (UserType) req.getSession()
                .getAttribute("user_type");
        if (!enabledUserTypes.contains(userType)) {
            throw new ProviderException("Access denied. ", 401);
        }
    }
}