package by.epam.provider.model.dto;


import by.epam.provider.model.Discount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountDto {
    private long id;

    private long tariffId;

    private String tariffName;

    private LocalDate startDate;

    private LocalDate finishDate;

    private Float percent;

    public DiscountDto(Discount discount) {
        this.id = discount.getId();
        this.tariffId = discount.getTariffId();
        this.startDate = discount.getStartDate();
        this.finishDate = discount.getFinishDate();
        this.percent = discount.getPercent();
    }
}
