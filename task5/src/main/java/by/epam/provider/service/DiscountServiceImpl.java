package by.epam.provider.service;

import by.epam.provider.dao.DiscountDao;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.model.Discount;
import by.epam.provider.model.dto.DiscountDto;

import java.util.List;
import java.util.Optional;

public class DiscountServiceImpl extends ServiceImpl implements DiscountService {


    /*--- create ---*/

    @Override
    public void add(Discount model) throws ProviderException {
        DiscountDao discountDao = this.transaction.createDao(DiscountDao.class);
        discountDao.create(model);
    }


    /*--- read ---*/

    @Override
    public List<DiscountDto> findAllDto() throws ProviderException {
        DiscountDao discountDao = this.transaction.createDao(DiscountDao.class);
        return discountDao.findAllDto();
    }

    @Override
    public List<Discount> findAll() throws ProviderException {
        DiscountDao discountDao = this.transaction.createDao(DiscountDao.class);
        return discountDao.findAll();
    }

    @Override
    public List<Discount> findAllInRange(long pageNum, long pageSize) throws ProviderException {
        DiscountDao discountDao = this.transaction.createDao(DiscountDao.class);
        return discountDao.findAllInRange(0L, 0L);
    }

    @Override
    public Optional<Discount> findById(long id) throws ProviderException {
        DiscountDao discountDao = this.transaction.createDao(DiscountDao.class);
        return discountDao.findById(id);
    }

    /*--- update ---*/

    @Override
    public void update(Discount model) throws ProviderException {
        DiscountDao discountDao = this.transaction.createDao(DiscountDao.class);
        discountDao.update(model);
    }


    /*--- delete ---*/

    @Override
    public void removeById(long id) throws ProviderException {
        DiscountDao discountDao = this.transaction.createDao(DiscountDao.class);
        discountDao.removeById(id);
    }


    /*--- service ---*/

    @Override
    public long size() throws ProviderException {
        DiscountDao discountDao = this.transaction.createDao(DiscountDao.class);
        return discountDao.size();
    }
}
