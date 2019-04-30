package by.epam.provider.command;

import by.epam.provider.command.ServletCommand;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.factory.ServiceFactoryImpl;
import by.epam.provider.model.dto.DiscountDto;
import by.epam.provider.security.UserType;
import by.epam.provider.service.DiscountService;
import by.epam.provider.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DiscountsCommand extends ServletCommand {

    public DiscountsCommand(ServiceFactoryImpl serviceFactoryNew) {
        super(serviceFactoryNew);
        this.enabledUserTypes.add(UserType.GUEST);
        this.enabledUserTypes.add(UserType.CLIENT);
        this.enabledUserTypes.add(UserType.ADMIN);
    }

    @Override
    public void executeGet(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        try {
            DiscountService discountService
                    = this.serviceFactory.findService(DiscountService.class);
            List<DiscountDto> discounts = discountService.findAllDto();
            req.setAttribute("discounts", discounts);
            req.getRequestDispatcher("/jsp/discounts.jsp")
                    .forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new ProviderException(e, 500);
        }
    }

    @Override
    public void executePost(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        try {
            UserService userService
                    = this.serviceFactory.findService(UserService.class);

            long discountId = Long.parseLong(req.getParameter("discountId"));
            long userId = Long.parseLong(req.getParameter("userId"));

            if (userService.addDiscount(userId, discountId)) {
                resp.sendRedirect(req.getContextPath()
                        + "/discounts?message="
                        + "Discount has been added to your tariff.");
            } else {
                resp.sendRedirect(req.getContextPath()
                        + "/discounts?message="
                        + "You must set this tariff to use discount. ");
            }
        } catch (IOException e) {
            throw new ProviderException(e, 500);
        } catch (NullPointerException e) {
            throw new ProviderException("Bad request", 400);
        }
    }
}
