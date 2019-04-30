package by.epam.provider.model;

import by.epam.provider.security.UserType;
import lombok.*;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private long id;

    private String login;

    private String hashPassword;

    private String saltPassword;

    private UserType type;

    private UserStatus status;

    public enum UserStatus {
        ACTIVE,
        BANNED,
        REMOVED
    }
}
