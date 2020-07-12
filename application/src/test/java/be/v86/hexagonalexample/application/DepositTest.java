package be.v86.hexagonalexample.application;

import be.v86.hexagonalexample.domain.Account;
import be.v86.hexagonalexample.domain.AccountId;
import be.v86.hexagonalexample.domain.AccountNotFoundException;
import be.v86.hexagonalexample.domain.AccountRepository;
import be.v86.hexagonalexample.domain.Balance;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DepositTest {

    private AccountRepository accountRepository = new TestAccountRepository();
    private Deposit deposit = new Deposit(accountRepository);

    @Test
    void null_account_id() {
        DepositRequest depositRequest = new DepositRequest(null, 1);

        assertThatThrownBy(() -> deposit.execute(depositRequest))
                .isInstanceOf(InvalidAccountIdException.class);
    }

    @Test
    void zero_amount() {
        AccountId accountId = AccountId.BE();
        DepositRequest depositRequest = new DepositRequest(accountId, 0);

        assertThatThrownBy(() -> deposit.execute(depositRequest))
                .isInstanceOf(InvalidAmountException.class);
    }

    @Test
    void negative_amount() {
        AccountId accountId = AccountId.BE();
        DepositRequest depositRequest = new DepositRequest(accountId, -1.5);

        assertThatThrownBy(() -> deposit.execute(depositRequest))
                .isInstanceOf(InvalidAmountException.class);
    }

    @Test
    void non_existing_account() {
        AccountId accountId = AccountId.BE();
        DepositRequest depositRequest = new DepositRequest(accountId, 50);

        assertThatThrownBy(() -> deposit.execute(depositRequest))
                .isInstanceOf(AccountNotFoundException.class);
    }

    @Test
    void success() {
        AccountId accountId = AccountId.BE();
        accountRepository.save(new Account(accountId));

        deposit.execute(new DepositRequest(accountId, 50));

        Account account = accountRepository.findById(accountId);
        assertThat(account.getBalance()).isEqualTo(new Balance(50));
    }

}
