package by.epam.provider.command;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.factory.ServiceFactoryImpl;
import by.epam.provider.model.User;
import by.epam.provider.security.UserType;
import by.epam.provider.service.UserService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Log4j2
public class UserListCommand extends ServletCommand {

    private static final int PAGE_SIZE = 5;

    public UserListCommand(final ServiceFactoryImpl serviceFactoryNew) {
        super(serviceFactoryNew);
        this.enabledUserTypes.add(UserType.ADMIN);
    }

    @Override
    public void executeGet(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        try {
            UserService userService
                    = this.serviceFactory.findService(UserService.class);

            int pageNum = Integer.parseInt(req.getParameter("page_num"));

            List<User> users = userService.findAllInRange(pageNum, PAGE_SIZE);

            req.setAttribute("users", users);
            req.setAttribute("page_num", pageNum);
            req.setAttribute("pages_num", userService.size() / PAGE_SIZE);

            req.getRequestDispatcher("/jsp/user_list.jsp")
                    .forward(req, resp);

        } catch (ServletException | IOException e) {
            throw new ProviderException(e, 500);
        } catch (NumberFormatException e) {
            throw new ProviderException("Invalid page. ", 400);
        }
    }
}
