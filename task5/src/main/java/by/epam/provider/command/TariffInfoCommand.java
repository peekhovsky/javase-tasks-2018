package by.epam.provider.command;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.factory.ServiceFactoryImpl;
import by.epam.provider.model.Tariff;
import by.epam.provider.security.UserType;
import by.epam.provider.service.TariffService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author Rostislav Pekhovksy 2019
 * @version 0.1
 */
public class TariffInfoCommand extends ServletCommand {

    public TariffInfoCommand(ServiceFactoryImpl serviceFactoryNew) {
        super(serviceFactoryNew);
        this.enabledUserTypes.add(UserType.GUEST);
        this.enabledUserTypes.add(UserType.CLIENT);
        this.enabledUserTypes.add(UserType.ADMIN);
    }

    @Override
    public void executeGet(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        try {
            String idStr = req.getParameter("id");

            if (idStr != null) {
                long id = Long.parseLong(idStr);
                executeFindById(id, req, resp);
            } else {
                executeFindAll(req, resp);
            }
        } catch (ServletException | IOException e) {
            throw new ProviderException(e, 500);
        }
    }

    private void executeFindById(long id, HttpServletRequest req,
                                 HttpServletResponse resp)
            throws ServletException, IOException, ProviderException {
        TariffService service = this.serviceFactory.findService(TariffService.class);
        Optional<Tariff> tariff = service.findById(id);
        if (tariff.isPresent()) {
            req.setAttribute("tariff", tariff.get());
            req.getRequestDispatcher("/jsp/tariff_info.jsp").forward(req, resp);
        } else {
            throw new ProviderException("No tariff with this id. ", 404);
        }
    }

    private void executeFindAll(HttpServletRequest req,
                                HttpServletResponse resp)
            throws ServletException, IOException, ProviderException {

        TariffService service = this.serviceFactory.findService(TariffService.class);

        UserType userType = (UserType) req.getSession().getAttribute("user_type");

        List<Tariff> tariffs = userType == UserType.ADMIN ?
                service.findAll() : service.findAllActive();

        req.setAttribute("tariffs", tariffs);
        req.getRequestDispatcher("/jsp/tariffs.jsp").forward(req, resp);
    }
}
