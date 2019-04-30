package by.epam.provider.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {

    private long id;

    private String firstName;

    private String lastName;

    private LocalDate registrationDate;

    private String iconReference;

}
