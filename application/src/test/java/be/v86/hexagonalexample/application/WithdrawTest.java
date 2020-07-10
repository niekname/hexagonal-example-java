package be.v86.hexagonalexample.application;

import be.v86.hexagonalexample.domain.Account;
import be.v86.hexagonalexample.domain.AccountId;
import be.v86.hexagonalexample.domain.AccountRepository;
import be.v86.hexagonalexample.domain.Balance;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WithdrawTest {

    private AccountRepository accountRepository = new TestAccountRepository();
    private Withdraw withdraw = new Withdraw(accountRepository);

    @Test
    void null_account_id() {
        WithdrawRequest withdrawRequest = new WithdrawRequest(null, 1);

        assertThatThrownBy(() -> withdraw.execute(withdrawRequest))
                .isInstanceOf(InvalidAccountIdException.class);
    }

    @Test
    void zero_amount() {
        AccountId accountId = AccountId.BE();
        WithdrawRequest withdrawRequest = new WithdrawRequest(accountId, 0);

        assertThatThrownBy(() -> withdraw.execute(withdrawRequest))
                .isInstanceOf(InvalidAmountException.class);
    }

    @Test
    void negative_amount() {
        AccountId accountId = AccountId.BE();
        WithdrawRequest withdrawRequest = new WithdrawRequest(accountId, -1.5);

        assertThatThrownBy(() -> withdraw.execute(withdrawRequest))
                .isInstanceOf(InvalidAmountException.class);
    }

    @Test
    void non_existing_account() {
        AccountId accountId = AccountId.BE();
        WithdrawRequest withdrawRequest = new WithdrawRequest(accountId, 50);

        assertThatThrownBy(() -> withdraw.execute(withdrawRequest))
                .isInstanceOf(AccountRepository.AccountNotFoundException.class);
    }

    @Test
    void balance_insufficient() {
        AccountId accountId = AccountId.BE();
        accountRepository.save(new Account(accountId));

        WithdrawRequest withdrawRequest = new WithdrawRequest(accountId, 50);

        assertThatThrownBy(() -> withdraw.execute(withdrawRequest))
                .isInstanceOf(Account.BalanceInsufficientException.class);
    }

    @Test
    void success() {
        AccountId accountId = AccountId.BE();
        Account account = new Account(accountId);
        account.deposit(100);
        accountRepository.save(account);

        withdraw.execute(new WithdrawRequest(accountId, 51));

        assertThat(accountRepository
                .findById(accountId)
                .getBalance())
                .isEqualTo(new Balance(49));
    }
}