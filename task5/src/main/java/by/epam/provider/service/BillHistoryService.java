package by.epam.provider.service;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.Bill;
import by.epam.provider.model.Tariff;
import by.epam.provider.model.dto.BillDto;

import java.util.List;
import java.util.Optional;

public interface BillHistoryService extends Service<Bill> {

    List<Bill> findAllByUserId(long id) throws ProviderException;

    List<BillDto> findAllByUserIdDto(long id) throws ProviderException;
}
