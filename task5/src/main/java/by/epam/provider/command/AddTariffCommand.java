package by.epam.provider.command;

import by.epam.provider.command.ServletCommand;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.factory.ServiceFactoryImpl;
import by.epam.provider.model.Tariff;
import by.epam.provider.security.UserType;
import by.epam.provider.service.TariffService;
import by.epam.provider.validator.TariffValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddTariffCommand extends ServletCommand {

    public AddTariffCommand(ServiceFactoryImpl serviceFactoryNew) {
        super(serviceFactoryNew);
        this.enabledUserTypes.add(UserType.ADMIN);
    }

    @Override
    public void executeGet(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        try {
            req.getRequestDispatcher("/jsp/add_tariff.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new ProviderException(e, 502);
        }
    }

    @Override
    public void executePost(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        super.checkUserType(req);
        String freeMbPerMonthStr = req.getParameter("free_mb_per_month");
        String speedStr = req.getParameter("speed");
        String speedOverTrafficStr = req.getParameter("speed_over_traffic");
        String dayBillStr = req.getParameter("tariff_day_bill");
        String statusStr = req.getParameter("status");

        try {
            Tariff tariff = Tariff.builder()
                    .name(req.getParameter("tariff"))
                    .description(req.getParameter("description"))
                    .freeMbPerMonth(Integer.parseInt(freeMbPerMonthStr))
                    .speed(Integer.parseInt(speedStr))
                    .speedOverTraffic(Integer.parseInt(speedOverTrafficStr))
                    .dayBill(Long.parseLong(dayBillStr))
                    .status(Tariff.Status.valueOf(statusStr))
                    .build();

            TariffValidator.validateTariff(tariff);

            TariffService service = serviceFactory.findService(TariffService.class);
            service.add(tariff);

            resp.sendRedirect(req.getContextPath() + "/tariffs?message=" + "Tariff has been added");

        } catch (NullPointerException | NumberFormatException e) {
            throw new ProviderException(e, 400);
        } catch (IOException e) {
            throw new ProviderException(e, 500);
        }
    }
}
