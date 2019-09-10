package be.v86.hexagonalexample.application;

import be.v86.hexagonalexample.domain.Account;
import be.v86.hexagonalexample.domain.AccountId;
import be.v86.hexagonalexample.domain.AccountRepository;

public class CreateAccount {

    private final AccountRepository accountRepository;

    public CreateAccount(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountId execute() {
        Account account = new Account(AccountId.BE());
        accountRepository.save(account);
        return account.getAccountId();
    }
}
