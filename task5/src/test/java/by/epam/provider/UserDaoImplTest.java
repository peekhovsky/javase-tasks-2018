package by.epam.provider;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.User;
import by.epam.provider.property.AppProperty;
import by.epam.provider.security.UserType;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;


/**
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */

@Test
@Ignore
public class UserDaoImplTest {


    public UserDaoImplTest() throws ProviderException {
    }

    @BeforeTest
    private void init() {
        AppProperty.setPropertiesPath("application.properties");
    }

    @Test()
    public void testCreate() throws ProviderException {
        User user = User.builder()
                .login("_login")
                .hashPassword("_hash_password")
                .status(User.UserStatus.ACTIVE)
                .type(UserType.CLIENT)
                .build();

    }

    @AfterTest
    private void exit() {

    }
}
