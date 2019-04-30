package by.epam.provider.command;

import by.epam.provider.command.ServletCommand;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.form.UserForm;
import by.epam.provider.factory.ServiceFactoryImpl;
import by.epam.provider.security.UserType;
import by.epam.provider.service.UserService;
import by.epam.provider.validator.UserValidator;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Log4j2
public class SignUpCommand extends ServletCommand {

    public SignUpCommand(final ServiceFactoryImpl serviceFactoryNew) {
        super(serviceFactoryNew);
        this.enabledUserTypes.add(UserType.GUEST);
        this.enabledUserTypes.add(UserType.CLIENT);
        this.enabledUserTypes.add(UserType.ADMIN);
    }

    @Override
    public void executeGet(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        try {
            req.getRequestDispatcher("/jsp/sign_up.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            log.error(e.getMessage());
            throw new ProviderException(e);
        }
    }

    @Override
    public void executePost(HttpServletRequest req, HttpServletResponse resp)
            throws ProviderException {
        try {
            UserValidator userValidator = new UserValidator();

            String firstName = req.getParameter("first_name");
            String lastName = req.getParameter("last_name");
            String login = req.getParameter("login");
            String password = req.getParameter("password");

            UserForm userForm = UserForm.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .login(login)
                    .password(password)
                    .build();

            UserService userService
                    = this.serviceFactory.findService(UserService.class);

            userService.addClientUser(userForm);

            log.debug("User form to sign up: " + userForm);
            userValidator.validateUserForm(userForm);

            resp.sendRedirect(req.getContextPath() + "/sign_in?message="
                    + "Your profile has been registered. ");

        } catch (IOException e) {
            throw new ProviderException(e);
        }
    }
}
