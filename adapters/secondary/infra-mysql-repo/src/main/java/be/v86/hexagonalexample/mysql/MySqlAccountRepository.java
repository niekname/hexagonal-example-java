package be.v86.hexagonalexample.mysql;

import be.v86.hexagonalexample.domain.Account;
import be.v86.hexagonalexample.domain.AccountId;
import be.v86.hexagonalexample.domain.AccountNotFoundException;
import be.v86.hexagonalexample.domain.AccountRepository;

public class MySqlAccountRepository implements AccountRepository {

    @Override
    public Account findById(final AccountId accountId) throws AccountNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save(final Account account) {
        throw new UnsupportedOperationException();
    }

}
