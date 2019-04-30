package by.epam.provider.dao.jdbc;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.User;
import by.epam.provider.model.UserInfo;
import by.epam.provider.model.dto.UserDto;
import by.epam.provider.security.UserType;

import java.sql.*;


public final class DaoRegular {

    private DaoRegular() {
    }

    static long receiveKeyAfterExecute(PreparedStatement statement)
            throws ProviderException {
        try (ResultSet rs = statement.getGeneratedKeys()) {
            long userInfoKey;
            if (rs.next()) {
                userInfoKey = rs.getLong(1);
            } else {
                throw new ProviderException("Cannot get generated key.");
            }
            return userInfoKey;
        } catch (SQLException e) {
            throw new ProviderException(e);
        }
    }

    static User userFromResultSet(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .login(rs.getString("login"))

                .hashPassword(rs.getString("hash_password"))
                .saltPassword(rs.getString("salt_password"))
                .type(UserType.valueOf(
                        rs.getString("type")))
                .status(User.UserStatus.valueOf(
                        rs.getString("status")))
                .build();
    }

    static UserInfo userInfoFromResultSet(ResultSet rs) throws SQLException {
        return UserInfo.builder()
                .id(rs.getLong("user_id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .registrationDate(rs
                        .getDate("registration_date")
                        .toLocalDate())
                .iconReference(rs.getString("icon_reference"))
                .build();
    }

    public static UserDto userDtoFromResultSet(ResultSet rs) throws SQLException {
        return UserDto.builder()
                .id(rs.getLong("id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .registrationDate(rs
                        .getDate("registration_date")
                        .toLocalDate())
                .iconReference(rs.getString("icon_reference"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .registrationDate(rs
                        .getDate("registration_date")
                        .toLocalDate())
                .iconReference(rs.getString("icon_reference"))
                .build();
    }

    static Long size(String sql, Connection connection) throws ProviderException {
        Long size;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(
                    sql)) {
                if (rs.next()) {
                    size = rs.getLong(1);
                    return size;
                } else {
                    throw new ProviderException("Empty result set.");
                }
            }
        } catch (SQLException e) {
            throw new ProviderException(e);
        }
    }

}
