package by.epam.provider.model.dto;


import by.epam.provider.model.Bill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2
public class BillDto {

    private long id;

    private long tariffId;

    private String tariffName;

    private long userId;

    private double sumInDollars;

    private LocalDateTime date;

    public BillDto(Bill bill) {
        this.id = bill.getId();
        this.tariffId = bill.getTariffId();
        this.userId = bill.getUserId();
        this.sumInDollars = bill.getSum() / 100.0;
        this.date = bill.getDate();
    }
}
