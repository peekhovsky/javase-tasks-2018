package by.epam.provider.dao;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.Discount;
import by.epam.provider.model.DiscountUser;
import by.epam.provider.model.dto.DiscountDto;

import java.util.List;

public interface DiscountUserDao extends CrudDao<DiscountUser, Long> {

    List<DiscountUser> findAllByUserId(long userId) throws ProviderException;

    List<Discount> findAllByUserIdDiscount(long userId)
            throws ProviderException;

    void removeByUserId(Long userId, Long discountId)
            throws ProviderException;

    Long size() throws ProviderException;
}
