package by.epam.provider.model;


import lombok.*;
import lombok.extern.log4j.Log4j2;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2
public class ClientUser {

    private Long id;

    private Long balance;

    private Status status;

    private Long tariffId;

    public enum Status {
        ACTIVE,
        BLOCKED
    }
}
