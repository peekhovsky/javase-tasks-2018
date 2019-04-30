package by.epam.provider.service;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.transaction.ProviderTransaction;
import lombok.Getter;

public abstract class ServiceImpl {

    @Getter
    protected ProviderTransaction transaction;

    public void setTransaction(ProviderTransaction transactionNew) {
        this.transaction = transactionNew;
    }
}
