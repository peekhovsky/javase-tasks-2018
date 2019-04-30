package by.epam.provider.command;

import by.epam.provider.command.ServletCommand;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.factory.ServiceFactoryImpl;
import by.epam.provider.security.UserType;
import by.epam.provider.service.UserService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Log4j2
public class PayCommand extends ServletCommand {

    public PayCommand(ServiceFactoryImpl serviceFactoryNew) {
        super(serviceFactoryNew);
        this.enabledUserTypes.add(UserType.CLIENT);
    }

    @Override
    public void executeGet(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        try {
            req.getRequestDispatcher("/jsp/pay.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new ProviderException(e, 502);
        }
    }

    @Override
    public void executePost(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        try {
            UserService service = this.serviceFactory.findService(UserService.class);
            String moneyStr = req.getParameter("money");

            Long userId = (Long) req.getSession().getAttribute("user_id");

            if (moneyStr != null) {
                float money = Float.parseFloat(moneyStr);
                log.debug("Add money: " + money);
                service.addMoneyToBalance(userId, (long) (money * 100));
            } else {
                throw new ProviderException("Cannot find money property", 500);
            }
            resp.sendRedirect(req.getContextPath() + "/user_info");

        } catch (NullPointerException | IOException e) {
            throw new ProviderException(e, 500);
        } catch (NumberFormatException e) {
            throw new ProviderException(e, 401);
        }
    }
}
