package be.v86.hexagonalexample.application;

import be.v86.hexagonalexample.domain.Account;
import be.v86.hexagonalexample.domain.AccountId;
import be.v86.hexagonalexample.domain.AccountRepository;
import be.v86.hexagonalexample.domain.Balance;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateAccountTest {

    private AccountRepository accountRepository = new TestAccountRepository();
    private CreateAccount createAccount = new CreateAccount(accountRepository);

    @Test
    void generates_new_BE_IBAN() {
        String BE_IBAN_REGEX = "BE[a-zA-Z0-9]{2}\\s?([0-9]{4}\\s?){3}\\s?";
        AccountId accountId = createAccount.execute();
        assertThat(accountId.toString()).matches(BE_IBAN_REGEX);
    }

    @Test
    void new_account_has_balance_0() {
        AccountId accountId = createAccount.execute();
        Account account = accountRepository.findById(accountId);
        assertThat(account.getBalance()).isEqualTo(new Balance(0));
    }

}
