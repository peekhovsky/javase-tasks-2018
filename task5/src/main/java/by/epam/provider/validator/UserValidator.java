package by.epam.provider.validator;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.form.UserForm;

public class UserValidator {

    private static final int MIN_PASSWORD_SIZE = 6;

    private static final int MAX_PASSWORD_SIZE = 30;

    private static final int MIN_LOGIN_SIZE = 3;

    private static final int MAX_LOGIN_SIZE = 30;


    public UserForm validateUserForm(UserForm userForm) throws ProviderException {
        StringBuilder newMessage = new StringBuilder();

        if (userForm.getFirstName() == null || userForm.getFirstName().isEmpty()) {
            newMessage.append("First name should not be empty. ");
        }

        if (userForm.getLastName() == null || userForm.getLastName().isEmpty()) {
            newMessage.append("Last name should not be empty. ");
        }

        if (userForm.getLogin() == null || userForm.getLogin().length() < MIN_LOGIN_SIZE
                || userForm.getLogin().length() > MAX_LOGIN_SIZE) {
            newMessage.append("Login size should be in "
                    + MIN_LOGIN_SIZE + "-" + MAX_LOGIN_SIZE + "range. ");
        }

        if (userForm.getPassword() == null || userForm.getPassword().length() < MIN_PASSWORD_SIZE
                || userForm.getPassword().length() > MAX_PASSWORD_SIZE) {
            newMessage.append("Password size should be in "
                    + MIN_LOGIN_SIZE + "-" + MAX_LOGIN_SIZE + "range. ");
        }

        String message = newMessage.toString();

        if (message.isEmpty()) {
            return userForm;
        } else {
            throw new ProviderException(message);
        }
    }
}
