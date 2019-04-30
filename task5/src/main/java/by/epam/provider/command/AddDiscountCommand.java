package by.epam.provider.command;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.factory.ServiceFactoryImpl;
import by.epam.provider.model.Discount;
import by.epam.provider.model.Tariff;
import by.epam.provider.model.dto.BillDto;
import by.epam.provider.security.UserType;
import by.epam.provider.service.BillHistoryService;
import by.epam.provider.service.DiscountService;
import by.epam.provider.service.TariffService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Log4j2
public class AddDiscountCommand extends ServletCommand {

    public AddDiscountCommand(ServiceFactoryImpl serviceFactoryNew) {
        super(serviceFactoryNew);
        this.enabledUserTypes.add(UserType.ADMIN);
    }

    @Override
    public void executeGet(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        try {
            TariffService service = this.serviceFactory.findService(TariffService.class);
            long tariffId = Long.parseLong(req.getParameter("id"));

            Optional<Tariff> tariffOptional = service.findById(tariffId);
            if (tariffOptional.isPresent()) {
                req.setAttribute("tariff", tariffOptional.get());
            } else {
                throw new ProviderException(new NoSuchElementException(), 400);
            }
            req.getRequestDispatcher("/jsp/add_discount.jsp")
                    .forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new ProviderException(e, 502);
        } catch (NullPointerException | NumberFormatException e) {
            throw new ProviderException(e, 400);
        }
    }

    @Override
    public void executePost(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        try {
            DiscountService service
                    = this.serviceFactory.findService(DiscountService.class);

            Discount discount = Discount.builder()
                    .tariffId(Long.parseLong(req.getParameter("id")))
                    .startDate(LocalDate.parse(req.getParameter("start_date")))
                    .finishDate(LocalDate.parse(req.getParameter("start_date")))
                    .percent(Float.parseFloat(req.getParameter("percent")))
                    .build();
            service.add(discount);

            String message = "Discount has been added. ";
            resp.sendRedirect(req.getContextPath()
                    + "/discounts?message=" + message);

        } catch (IOException e) {
            throw new ProviderException(e, 502);
        }
    }
}
