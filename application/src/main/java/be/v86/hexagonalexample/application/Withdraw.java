package be.v86.hexagonalexample.application;

import be.v86.hexagonalexample.domain.Account;
import be.v86.hexagonalexample.domain.AccountRepository;

public class Withdraw {

    private final AccountRepository accountRepository;

    public Withdraw(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void execute(final WithdrawRequest withdrawRequest) {
        validateRequest(withdrawRequest);

        Account account = accountRepository.findById(withdrawRequest.accountId);
        account.withdraw(withdrawRequest.amount);
        accountRepository.save(account);
    }

    private void validateRequest(final WithdrawRequest withdrawRequest) {
        if (withdrawRequest.accountId == null)
            throw new InvalidAccountIdException();

        if (withdrawRequest.amount <= 0)
            throw new InvalidAmountException();
    }
}
