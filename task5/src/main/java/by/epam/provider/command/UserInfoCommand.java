package by.epam.provider.command;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.factory.ServiceFactoryImpl;
import by.epam.provider.model.Discount;
import by.epam.provider.model.dto.UserDto;
import by.epam.provider.security.UserType;
import by.epam.provider.service.UserService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Log4j2
public class UserInfoCommand extends ServletCommand {

    public UserInfoCommand(final ServiceFactoryImpl serviceFactoryNew) {
        super(serviceFactoryNew);
        this.enabledUserTypes.add(UserType.CLIENT);
        this.enabledUserTypes.add(UserType.ADMIN);
    }

    @Override
    public void executeGet(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        try {
            UserService userService
                    = this.serviceFactory.findService(UserService.class);

            UserType userType = (UserType) req.getSession()
                    .getAttribute("user_type");

            Long userId;
            if (userType == UserType.ADMIN) {
                userId = Long.parseLong(req.getParameter("id"));
                log.debug("Id: " + userId);
            } else if (userType == UserType.CLIENT) {
                userId = (Long) req.getSession().getAttribute("user_id");
            } else {
                throw new ProviderException("Access denied. ", 401);
            }

            if (userId != null) {
                Optional<UserDto> userDtoOptional
                        = userService.findUserDtoById(userId);
                List<Discount> discounts = userService.discountsByUserId(userId);

                req.setAttribute("discounts", discounts);

                if (userDtoOptional.isPresent()) {
                    req.setAttribute("user_dto", userDtoOptional.get());
                    req.getRequestDispatcher("/jsp/user_info.jsp")
                            .forward(req, resp);
                } else {
                    throw new ProviderException("Cannot find user in client_user table. ", 404);
                }
            } else {
                throw new ProviderException("Cannot find user in user table. ", 404);
            }

        } catch (ServletException | IOException e) {
            throw new ProviderException(e);
        }
    }

    @Override
    public void executePost(HttpServletRequest req, HttpServletResponse resp) throws ProviderException {

        if (req.getParameter("is_photo") != null) {
            long userId = Long.parseLong(req.getParameter("id"));
            try {
                String uploadPath = req.getServletContext()
                        .getRealPath("/static/img");
                log.info("Upload path: " + uploadPath);
                Part part = req.getPart("photo");
                String fileName = "logo" + userId + "."
                        + getExtensionByStringHandling(
                        part.getSubmittedFileName()).orElse("jpg");

                part.write(uploadPath + File.separator + fileName);
                log.info("File name: " + part.getSubmittedFileName());

                UserService service
                        = this.serviceFactory.findService(UserService.class);
                service.updateUserLogo(userId, fileName);

                resp.sendRedirect(req.getRequestURI()
                        + "?message=Logo has been uploaded.&id=" + userId);

            } catch (IOException | ServletException e) {
                throw new ProviderException(e, 500);
            }

        } else {
            try {
                UserService service
                        = this.serviceFactory.findService(UserService.class);
                Long userId = (Long) req.getSession().getAttribute("user_id");
                String login = req.getParameter("login");
                String firstName = req.getParameter("first_name");
                String lastName = req.getParameter("last_name");

                if (!login.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty()) {
                    service.updateLoginNameSurname(userId, login, firstName, lastName);
                } else {
                    throw new ProviderException("Login or first name or last name "
                            + "is empty. ", 400);
                }
                resp.sendRedirect(req.getRequestURI() + "?id=" + userId);

            } catch (NullPointerException e) {
                throw new ProviderException("Login or first name or last name "
                        + "is empty. ", 400);

            } catch (IOException e) {
                throw new ProviderException(e);
            }
        }
    }

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
