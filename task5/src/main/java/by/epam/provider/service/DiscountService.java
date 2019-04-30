package by.epam.provider.service;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.Discount;
import by.epam.provider.model.Tariff;
import by.epam.provider.model.dto.DiscountDto;

import java.util.List;
import java.util.Optional;

public interface DiscountService extends Service<Discount> {
    List<DiscountDto> findAllDto() throws ProviderException;
}
