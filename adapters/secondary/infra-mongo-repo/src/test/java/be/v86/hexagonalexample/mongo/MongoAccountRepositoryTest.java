package be.v86.hexagonalexample.mongo;

import be.v86.hexagonalexample.domain.Account;
import be.v86.hexagonalexample.domain.AccountId;
import be.v86.hexagonalexample.domain.AccountNotFoundException;
import be.v86.hexagonalexample.domain.AccountRepository;
import be.v86.hexagonalexample.domain.Balance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataMongoTest
@ContextConfiguration(classes = MongoAccountRepository.class)
@EnableAutoConfiguration
public class MongoAccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void findNonExistingAccount() {
        assertThatThrownBy(() -> accountRepository.findById(AccountId.BE()))
                .isInstanceOf(AccountNotFoundException.class);
    }

    @Test
    void saveAndFind() {
        Account account = new Account(AccountId.BE());
        account.deposit(50);
        accountRepository.save(account);

        Account result = accountRepository.findById(account.getAccountId());

        assertThat(result.getBalance()).isEqualTo(new Balance(50));
    }
}
