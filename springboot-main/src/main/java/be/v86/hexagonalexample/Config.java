package be.v86.hexagonalexample;

import be.v86.hexagonalexample.application.CreateAccount;
import be.v86.hexagonalexample.application.Deposit;
import be.v86.hexagonalexample.application.Withdraw;
import be.v86.hexagonalexample.domain.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Autowired
    private AccountRepository accountRepository;

    @Bean
    public CreateAccount createAccount() {
        return new CreateAccount(accountRepository);
    }

    @Bean
    public Deposit deposit() {
        return new Deposit(accountRepository);
    }

    @Bean
    public Withdraw withdraw() {
        return new Withdraw(accountRepository);
    }

}
