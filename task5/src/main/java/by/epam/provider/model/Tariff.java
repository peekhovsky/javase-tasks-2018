package by.epam.provider.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tariff {

    private long id;

    private String name;

    private String description;

    private int freeMbPerMonth;

    private int speed;

    private int speedOverTraffic;

    private long dayBill;

    private Status status;

    public enum Status {
        ACTIVE,
        NO_NEW_USERS,
        DELETED
    }
}
