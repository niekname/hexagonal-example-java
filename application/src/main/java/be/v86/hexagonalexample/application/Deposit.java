package be.v86.hexagonalexample.application;

import be.v86.hexagonalexample.domain.Account;
import be.v86.hexagonalexample.domain.AccountRepository;

public class Deposit {

    private final AccountRepository accountRepository;

    public Deposit(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void execute(final DepositRequest depositRequest) {
        validateRequest(depositRequest);

        Account account = accountRepository.findById(depositRequest.accountId);
        account.deposit(depositRequest.amount);
        accountRepository.save(account);
    }

    private void validateRequest(final DepositRequest depositRequest) {
        if (depositRequest.accountId == null)
            throw new InvalidAccountIdException();

        if (depositRequest.amount <= 0)
            throw new InvalidAmountException();
    }

    class InvalidAccountIdException extends RuntimeException {
    }

    class InvalidAmountException extends RuntimeException {
    }

}
