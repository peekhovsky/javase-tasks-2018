package by.epam.provider.service;

import by.epam.provider.dao.BillHistoryDao;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.Bill;
import by.epam.provider.model.dto.BillDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class BillHistoryServiceImpl extends ServiceImpl implements BillHistoryService {

    @Override
    public List<Bill> findAll() throws ProviderException {
        BillHistoryDao billHistoryDao
                = this.transaction.createDao(BillHistoryDao.class);
        return billHistoryDao.findAll();
    }

    @Override
    public List<Bill> findAllInRange(long pageNum, long pageSize) throws ProviderException {
        return Collections.emptyList();
    }

    @Override
    public List<Bill> findAllByUserId(long id) throws ProviderException {
        BillHistoryDao billHistoryDao
                = this.transaction.createDao(BillHistoryDao.class);
        return billHistoryDao.findAllByUserId(id);
    }

    @Override
    public List<BillDto> findAllByUserIdDto(long id) throws ProviderException {
        BillHistoryDao billHistoryDao
                = this.transaction.createDao(BillHistoryDao.class);
        return billHistoryDao.findAllByUserIdDto(id);
    }

    @Override
    public Optional<Bill> findById(long id) throws ProviderException {
        BillHistoryDao billHistoryDao
                = this.transaction.createDao(BillHistoryDao.class);
        return billHistoryDao.findById(id);
    }

    @Override
    public void add(Bill bill) throws ProviderException {
        BillHistoryDao billHistoryDao
                = this.transaction.createDao(BillHistoryDao.class);
        billHistoryDao.create(bill);
    }

    @Override
    public void update(Bill bill) throws ProviderException {
        BillHistoryDao billHistoryDao
                = this.transaction.createDao(BillHistoryDao.class);
        billHistoryDao.update(bill);
    }

    @Override
    public void removeById(long id) throws ProviderException {
        throw new ProviderException(new UnsupportedOperationException(), 503);
    }


    @Override
    public long size() throws ProviderException {
        throw new ProviderException(new UnsupportedOperationException());
    }
}
