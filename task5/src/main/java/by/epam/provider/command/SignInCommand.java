package by.epam.provider.command;

import by.epam.provider.command.ServletCommand;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.User;
import by.epam.provider.model.UserInfo;
import by.epam.provider.model.form.UserForm;
import by.epam.provider.factory.ServiceFactoryImpl;
import by.epam.provider.security.UserType;
import by.epam.provider.service.UserService;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Log4j2
public class SignInCommand extends ServletCommand {

    public SignInCommand(ServiceFactoryImpl serviceFactoryNew) {
        super(serviceFactoryNew);
        this.enabledUserTypes.add(UserType.GUEST);
        this.enabledUserTypes.add(UserType.CLIENT);
        this.enabledUserTypes.add(UserType.ADMIN);
    }

    @Override
    public void executeGet(final HttpServletRequest req,
                           final HttpServletResponse resp) throws ProviderException {
        try {
            req.getRequestDispatcher("/jsp/sign_in.jsp")
                    .forward(req, resp);
        } catch (ServletException | IOException e) {
            log.error(e.getMessage());
            throw new ProviderException(e);
        }
    }

    @Override
    public void executePost(final HttpServletRequest req,
                            final HttpServletResponse resp) throws ProviderException {
        try {
            UserService userService
                    = this.serviceFactory.findService(UserService.class);

            Objects.requireNonNull(userService);

            log.debug("Found user service: " + userService.getClass().getName());

            UserForm userForm = UserForm.builder()
                    .login(req.getParameter("login"))
                    .password(req.getParameter("password"))
                    .build();

            Optional<User> userOptional = userService
                    .findUserByLoginAndCheckPassword(userForm);

            if (userOptional.isPresent()) {
                User user = userOptional.get();

                HttpSession session = req.getSession(true);

                Optional<UserInfo> userInfoOptional
                        = userService.findUserInfoById(user.getId());

                userInfoOptional.ifPresent(userInfo
                        -> session.setAttribute("avatar",
                        userInfo.getIconReference()));

                session.setAttribute("user_login", user.getLogin());
                session.setAttribute("user_id", user.getId());
                session.setAttribute("user_type", user.getType());
                req.getRequestDispatcher("/jsp/index.jsp")
                        .forward(req, resp);
            } else {
                req.setAttribute("errorMessage",
                        "Login or password is not valid. ");
                req.getRequestDispatcher("/jsp/sign_in.jsp")
                        .forward(req, resp);
            }
        } catch (ServletException | IOException e) {
            throw new ProviderException(e);
        }
    }

}
