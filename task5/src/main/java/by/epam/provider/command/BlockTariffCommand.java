package by.epam.provider.command;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.factory.ServiceFactoryImpl;
import by.epam.provider.security.UserType;
import by.epam.provider.service.TariffService;
import by.epam.provider.service.UserService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Log4j2
public class BlockTariffCommand extends ServletCommand {

    public BlockTariffCommand(final ServiceFactoryImpl serviceFactoryNew) {
        super(serviceFactoryNew);
        this.enabledUserTypes.add(UserType.ADMIN);
    }

    @Override
    public void executeGet(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        try {
            TariffService service
                    = this.serviceFactory.findService(TariffService.class);

            long id = Long.parseLong(req.getParameter("id"));
            String action = req.getParameter("action");

            switch (action.toUpperCase()) {
                case "BLOCK":
                    service.blockTariff(id);
                    resp.sendRedirect(req.getContextPath() + "/tariffs"
                            + "?message=Tariff has been blocked. ");
                    break;
                case "UNBLOCK":
                    service.unblockTariff(id);
                    resp.sendRedirect(req.getContextPath() + "/tariffs?"
                            + "?message=Tariff has been unblocked. ");
                    break;
                default:
                    throw new ProviderException("Bad request", 400);
            }

        } catch (IOException e) {
            throw new ProviderException(e, 500);
        } catch (NumberFormatException | NullPointerException e) {
            throw new ProviderException("Bad request. ", 400);
        }
    }
}
