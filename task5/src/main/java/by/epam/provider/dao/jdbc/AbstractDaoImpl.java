package by.epam.provider.dao.jdbc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Connection;

@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractDaoImpl {
    @Getter
    @Setter
    protected Connection connection;
}
