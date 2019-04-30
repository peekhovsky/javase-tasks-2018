package by.epam.provider.service;

import by.epam.provider.dao.TariffDao;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.Tariff;

import java.util.List;
import java.util.Optional;

public class TariffServiceImpl extends ServiceImpl implements TariffService {

    @Override
    public List<Tariff> findAll() throws ProviderException {
        TariffDao dao = this.transaction.createDao(TariffDao.class);
        return dao.findAll();
    }

    @Override
    public List<Tariff> findAllActive() throws ProviderException {
        TariffDao dao = this.transaction.createDao(TariffDao.class);
        return dao.findAllActive();
    }

    @Override
    public List<Tariff> findAllInRange(long pageNum, long pageSize)
            throws ProviderException {
        TariffDao dao = this.transaction.createDao(TariffDao.class);

        long startNum = pageNum * pageSize;
        long endNum = startNum + 10L;

        return dao.findAllInRange(startNum, endNum);
    }

    @Override
    public Optional<Tariff> findById(long id) throws ProviderException {
        TariffDao dao = this.transaction.createDao(TariffDao.class);
        return dao.findById(id);
    }

    @Override
    public void add(Tariff tariff) throws ProviderException {
        TariffDao dao = this.transaction.createDao(TariffDao.class);
        dao.create(tariff);
    }

    @Override
    public void update(Tariff tariff) throws ProviderException {
        TariffDao dao = this.transaction.createDao(TariffDao.class);
        dao.update(tariff);
    }

    @Override
    public void blockTariff(final long tariffId) throws ProviderException {
        TariffDao dao = this.transaction.createDao(TariffDao.class);
        Optional<Tariff> tariffOptional = dao.findById(tariffId);

        if (tariffOptional.isPresent()) {
            Tariff tariff = tariffOptional.get();
            tariff.setStatus(Tariff.Status.NO_NEW_USERS);
            dao.update(tariff);
        } else {
            throw new ProviderException("Cannot find tariff. ", 404);
        }
    }

    @Override
    public void unblockTariff(final long tariffId) throws ProviderException {
        TariffDao dao = this.transaction.createDao(TariffDao.class);
        Optional<Tariff> tariffOptional = dao.findById(tariffId);

        if (tariffOptional.isPresent()) {
            Tariff tariff = tariffOptional.get();
            tariff.setStatus(Tariff.Status.ACTIVE);
            dao.update(tariff);
        } else {
            throw new ProviderException("Cannot find tariff. ", 404);
        }
    }


    @Override
    public void removeById(long id) throws ProviderException {
        throw new ProviderException(new UnsupportedOperationException(), 501);
    }

    @Override
    public long size() throws ProviderException {
        TariffDao dao = this.transaction.createDao(TariffDao.class);
        return dao.size();
    }
}
