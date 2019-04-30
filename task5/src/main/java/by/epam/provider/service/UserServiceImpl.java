package by.epam.provider.service;


import by.epam.provider.dao.*;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.*;
import by.epam.provider.model.dto.UserDto;
import by.epam.provider.model.form.UserForm;
import by.epam.provider.security.PasswordEncrypt;
import by.epam.provider.security.UserType;
import lombok.extern.log4j.Log4j2;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * @author Rostislav Pekhovsky 2019
 * @version 0.1
 */
@Log4j2
public class UserServiceImpl extends ServiceImpl implements UserService {


    public UserServiceImpl() {
    }

    //------ create ------//

    @Override
    public void add(User model) throws ProviderException {
        throw new ProviderException(new UnsupportedOperationException(), 503);
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void addClientUser(final UserForm userForm) throws ProviderException {
        UserDao userDao = this.transaction.createDao(UserDao.class);
        UserInfoDao userInfoDao = this.transaction.createDao(UserInfoDao.class);
        ClientUserDao clientUserDao = this.transaction.createDao(ClientUserDao.class);

        ClientUser clientUser = ClientUser.builder()
                .balance(0L)
                .status(ClientUser.Status.ACTIVE)
                .build();

        User user = userFromUserForm(userForm);
        long userKey = userDao.create(user);

        UserInfo userInfo = userInfoFromUserForm(userForm);
        userInfo.setId(userKey);
        clientUser.setId(userKey);

        userInfoDao.create(userInfo);
        clientUserDao.create(clientUser);
    }

    //------ read ------//

    @Override
    @SuppressWarnings("Duplicates")
    public Optional<UserDto> findUserDtoById(final long id) throws ProviderException {
        UserDao userDao = this.transaction.createDao(UserDao.class);
        UserInfoDao userInfoDao = this.transaction.createDao(UserInfoDao.class);
        ClientUserDao clientUserDao = this.transaction.createDao(ClientUserDao.class);
        TariffDao tariffDao = this.transaction.createDao(TariffDao.class);

        Optional<User> userOptional = userDao.findById(id);
        Optional<UserInfo> userInfoOptional = userInfoDao.findById(id);
        Optional<ClientUser> clientUserOptional = clientUserDao.findById(id);

        if (userOptional.isPresent() && userInfoOptional.isPresent()
                && clientUserOptional.isPresent()) {

            UserDto userDto = new UserDto(userOptional.get(),
                    userInfoOptional.get(), clientUserOptional.get());

            Long tariffId = clientUserOptional.get().getTariffId();

            log.debug("Tariff id: " + tariffId);

            if (tariffId != null && tariffId > 0) {
                Optional<Tariff> tariff
                        = tariffDao.findById(tariffId);

                if (tariff.isPresent()) {
                    userDto.setTariffName(tariff.get().getName());
                } else {
                    throw new ProviderException(
                            "Cannot find an appropriate tariffId. ", 501);
                }
            } else {
                log.warn("Not valid tariff id. ");
            }
            return Optional.of(userDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() throws ProviderException {
        UserDao userDao = this.transaction.createDao(UserDao.class);
        return userDao.findAll();
    }

    @Override
    public List<User> findAllInRange(long pageNum, long pageSize)
            throws ProviderException {
        UserDao userDao = this.transaction.createDao(UserDao.class);
        return userDao.findAllInRange(pageNum * pageSize, pageSize);
    }


    @Override
    public Optional<User> findById(final long id) throws ProviderException {
        UserDao userDao = this.transaction.createDao(UserDao.class);
        return userDao.findById(id);
    }


    @Override
    public Optional<User> findUserByLogin(final String login) throws ProviderException {
        UserDao userDao = this.transaction.createDao(UserDao.class);
        return userDao.findUserByLogin(login);
    }

    @Override
    public Optional<User> findUserByLoginAndCheckPassword(UserForm userForm) throws ProviderException {
        Optional<User> userOptional = findUserByLogin(userForm.getLogin());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (PasswordEncrypt.verifyUserPassword(userForm.getPassword(),
                    user.getHashPassword(), user.getSaltPassword())) {
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserInfo> findUserInfoById(final long id)
            throws ProviderException {
        UserInfoDao userInfoDao = this.transaction.createDao(UserInfoDao.class);
        return userInfoDao.findById(id);
    }


    //------ update ------ //

    @Override
    public void update(User model) throws ProviderException {
        throw new ProviderException(new UnsupportedOperationException(), 501);
    }

    @Override
    public void updateUserTariff(final long userId, final long tariffId)
            throws ProviderException {
        ClientUserDao clientUserDao
                = this.transaction.createDao(ClientUserDao.class);
        DiscountUserDao discountUserDao
                = this.transaction.createDao(DiscountUserDao.class);

        Optional<ClientUser> clientUserOptional = clientUserDao.findById(userId);
        if (clientUserOptional.isPresent()) {
            ClientUser clientUser = clientUserOptional.get();
            clientUser.setTariffId(tariffId);
            clientUserDao.update(clientUser);

            discountUserDao.removeByUserId(userId, tariffId);

        } else {
            throw new ProviderException("Cannot find user", 404);
        }
    }

    @Override
    public void updateUserLogo(final long userId, final String path)
            throws ProviderException {
        UserInfoDao userInfoDao = this.transaction.createDao(UserInfoDao.class);
        Optional<UserInfo> userInfoOptional = userInfoDao.findById(userId);

        if (userInfoOptional.isPresent()) {
            UserInfo userInfo = userInfoOptional.get();
            userInfo.setIconReference(path);
            userInfoDao.update(userInfo);
        } else {
            throw new ProviderException("Cannot find user. ", 404);
        }
    }


    @Override
    public void updateLoginNameSurname(final long userId, final String login,
                                       final String firstName,
                                       final String lastName)
            throws ProviderException {
        try {
            UserDao userDao = this.transaction.createDao(UserDao.class);
            UserInfoDao userInfoDao = this.transaction.createDao(UserInfoDao.class);
            Optional<User> userOptional = userDao.findById(userId);
            Optional<UserInfo> userInfoOptional = userInfoDao.findById(userId);

            if (userOptional.isPresent() && userInfoOptional.isPresent()) {
                User user = userOptional.get();
                UserInfo userInfo = userInfoOptional.get();
                user.setLogin(login);
                userInfo.setFirstName(firstName);
                userInfo.setLastName(lastName);
                userDao.update(user);
                userInfoDao.update(userInfo);
            } else {
                throw new ProviderException("Invalid user id. ", 400);
            }

        } catch (NullPointerException e) {
            throw new ProviderException("Cannot find user", 404);
        }
    }

    @Override
    public void addMoneyToBalance(final long userId, final long amount)
            throws ProviderException {
        ClientUserDao clientUserDao
                = this.transaction.createDao(ClientUserDao.class);
        clientUserDao.addBalance(amount, userId);

        Optional<ClientUser> clientUserOptional = clientUserDao.findById(userId);
        if (clientUserOptional.isPresent()) {
            ClientUser clientUser = clientUserOptional.get();
            if (clientUser.getBalance() >= 0) {
                clientUser.setStatus(ClientUser.Status.ACTIVE);
                clientUserDao.update(clientUser);
            }
        } else {
            throw new ProviderException("Cannot find client user. ", 500);
        }
    }

    @Override
    public void chargeUsers() throws ProviderException {
        ClientUserDao clientUserDao
                = this.transaction.createDao(ClientUserDao.class);
        BillHistoryDao billHistoryDao
                = this.transaction.createDao(BillHistoryDao.class);

        List<Bill> bills = clientUserDao.chargeClientUsers();
        billHistoryDao.createAll(bills);
    }

    @Override
    public void blockUsersWithNegativeBalance() throws ProviderException {
        ClientUserDao clientUserDao
                = this.transaction.createDao(ClientUserDao.class);
        clientUserDao.updateBlockUsersWithNegativeBalance();
    }

    @Override
    public void banUserById(long usedId) throws ProviderException {
        UserDao userDao = this.transaction.createDao(UserDao.class);
        Optional<User> userOptional = userDao.findById(usedId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(User.UserStatus.BANNED);
            userDao.update(user);
        } else {
            throw new ProviderException("Cannot find user. ", 404);
        }
    }

    @Override
    public void unbanUserById(long usedId) throws ProviderException {
        UserDao userDao = this.transaction.createDao(UserDao.class);
        Optional<User> userOptional = userDao.findById(usedId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(User.UserStatus.ACTIVE);
            userDao.update(user);
        } else {
            throw new ProviderException("Cannot find user. ", 404);
        }
    }


    @Override
    public boolean addDiscount(long userId, long discountId) throws ProviderException {

        DiscountDao discountDao = this.transaction.createDao(DiscountDao.class);
        ClientUserDao clientUserDao = this.transaction.createDao(ClientUserDao.class);

        Optional<Discount> discountOptional = discountDao.findById(discountId);
        Optional<ClientUser> clientUserOptional = clientUserDao.findById(userId);

        if (discountOptional.isPresent() && clientUserOptional.isPresent()) {
            Discount discount = discountOptional.get();
            ClientUser clientUser = clientUserOptional.get();
            log.debug("cc");
            if (discount.getTariffId() == clientUser.getTariffId()) {
                log.debug("aa");
                DiscountUser discountUser = DiscountUser.builder()
                        .userId(userId)
                        .discountId(discountId)
                        .build();

                DiscountUserDao discountUserDao
                        = this.transaction.createDao(DiscountUserDao.class);

                if (discountUserDao.findAllByUserId(userId).contains(discountUser)) {
                    return false;
                } else {
                    log.debug("bb");
                    discountUserDao.create(discountUser);
                    return true;
                }
            } else {
                return false;
            }
        } else {
            throw new ProviderException("Cannot find data by this ids. ", 400);
        }
    }

    @Override
    public List<Discount> discountsByUserId(long id) throws ProviderException {
        DiscountUserDao discountUserDao
                = this.transaction.createDao(DiscountUserDao.class);
        return discountUserDao.findAllByUserIdDiscount(id);
    }

    //------ delete ------ //
    @Override
    public void removeById(long id) throws ProviderException {
        UserDao userDao = this.transaction.createDao(UserDao.class);
        userDao.removeById(id);
    }


    //------ service ------//
    @Override
    public long size() throws ProviderException {
        UserDao userDao = this.transaction.createDao(UserDao.class);
        return userDao.size();
    }

    private User userFromUserForm(final UserForm userForm) {
        String password = userForm.getPassword();
        String salt = PasswordEncrypt.getSalt();
        String hashPassword
                = PasswordEncrypt.generateSecurePassword(password, salt);

        return User.builder()
                .login(userForm.getLogin())
                .hashPassword(hashPassword)
                .saltPassword(salt)
                .status(User.UserStatus.ACTIVE)
                .type(UserType.CLIENT)
                .build();
    }

    private UserInfo userInfoFromUserForm(final UserForm userForm) {
        return UserInfo.builder()
                .firstName(userForm.getFirstName())
                .lastName(userForm.getLastName())
                .registrationDate(LocalDate.now())
                .build();
    }
}
