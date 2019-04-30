package by.epam.provider.model;


import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2
public class Bill {

    private long id;

    private long tariffId;

    private long userId;

    private long sum;

    private LocalDateTime date;
}
