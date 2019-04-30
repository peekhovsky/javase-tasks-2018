package by.epam.provider.command;

import by.epam.provider.command.ServletCommand;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.factory.ServiceFactoryImpl;
import by.epam.provider.model.dto.BillDto;
import by.epam.provider.security.UserType;
import by.epam.provider.service.BillHistoryService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;


@Log4j2
public class BillsCommand extends ServletCommand {

    public BillsCommand(ServiceFactoryImpl serviceFactoryNew) {
        super(serviceFactoryNew);
        this.enabledUserTypes.add(UserType.CLIENT);
        this.enabledUserTypes.add(UserType.ADMIN);
    }

    @Override
    public void executeGet(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        try {
            String id = req.getParameter("id");
            if (id != null) {
                BillHistoryService service
                        = this.serviceFactory.findService(BillHistoryService.class);
                List<BillDto> bills = service.findAllByUserIdDto(Long.parseLong(id));
                Collections.reverse(bills);
                req.setAttribute("bills", bills);
                req.getRequestDispatcher("/jsp/bills.jsp").forward(req, resp);
            } else {
                throw new ProviderException("Id is not valid. ", 400);
            }
        } catch (ServletException | IOException e) {
            throw new ProviderException(e, 502);
        }
    }
}
