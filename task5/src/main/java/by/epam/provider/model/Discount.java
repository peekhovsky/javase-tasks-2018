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
public class Discount {
    private long id;

    private long tariffId;

    private LocalDate startDate;

    private LocalDate finishDate;

    private Float percent;
}
