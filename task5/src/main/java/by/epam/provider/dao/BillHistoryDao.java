package by.epam.provider.dao;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.Bill;
import by.epam.provider.model.dto.BillDto;

import java.util.List;

public interface BillHistoryDao extends CrudDao<Bill, Long> {
    void createAll(List<Bill> bills) throws ProviderException;

    List<BillDto> findAllByUserIdDto(long id) throws ProviderException;

    List<Bill> findAllByUserId(long id) throws ProviderException;

    List<Bill> findAllByUserIdInRange(long id, int pageNum) throws ProviderException;
}
