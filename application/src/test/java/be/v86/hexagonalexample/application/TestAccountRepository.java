package be.v86.hexagonalexample.application;

import be.v86.hexagonalexample.domain.Account;
import be.v86.hexagonalexample.domain.AccountId;
import be.v86.hexagonalexample.domain.AccountRepository;

import java.util.ArrayList;
import java.util.List;

class TestAccountRepository implements AccountRepository {

    private List<Account> accounts = new ArrayList<>();

    @Override
    public Account findById(final AccountId accountId) {
        return accounts
                .stream()
                .filter(a -> a.getAccountId().equals(accountId))
                .findFirst()
                .orElseThrow(AccountNotFoundException::new);
    }

    @Override
    public void save(final Account account) {
        accounts.add(account);
    }
}
