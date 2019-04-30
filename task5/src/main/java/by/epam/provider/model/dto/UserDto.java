package by.epam.provider.model.dto;

import by.epam.provider.model.ClientUser;
import by.epam.provider.model.User;
import by.epam.provider.model.UserInfo;
import by.epam.provider.security.UserType;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;

@Log4j2
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String login;

    private String hashPassword;

    private UserType type;

    private User.UserStatus userStatus;

    private String firstName;

    private String lastName;

    private LocalDate registrationDate;

    private String iconReference;

    private Double balanceInDollars;

    private ClientUser.Status clientUserStatus;

    private Long tariffId;

    @Builder.Default
    private String tariffName = "";

    public UserDto(final User user, final UserInfo userInfo) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.hashPassword = user.getHashPassword();
        this.type = user.getType();
        this.userStatus = user.getStatus();
        this.firstName = userInfo.getFirstName();
        this.lastName = userInfo.getLastName();
        this.registrationDate = userInfo.getRegistrationDate();
        this.iconReference = userInfo.getIconReference();
    }

    public UserDto(final User user, final UserInfo userInfo, final ClientUser clientUser) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.hashPassword = user.getHashPassword();
        this.type = user.getType();
        this.userStatus = user.getStatus();
        this.firstName = userInfo.getFirstName();
        this.lastName = userInfo.getLastName();
        this.registrationDate = userInfo.getRegistrationDate();
        this.iconReference = userInfo.getIconReference();
        this.balanceInDollars = clientUser.getBalance() / 100.0;
        this.clientUserStatus = clientUser.getStatus();
        this.tariffId = clientUser.getTariffId();
    }
}
