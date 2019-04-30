package by.epam.provider.dao;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.Discount;
import by.epam.provider.model.Tariff;
import by.epam.provider.model.dto.DiscountDto;

import java.util.List;

public interface DiscountDao extends CrudDao<Discount, Long> {

    List<DiscountDto> findAllDto() throws ProviderException;

    Long size() throws ProviderException;
}
