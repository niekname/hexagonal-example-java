package be.v86.hexagonalexample.mongo;

import be.v86.hexagonalexample.domain.Account;
import be.v86.hexagonalexample.domain.AccountId;
import be.v86.hexagonalexample.domain.Balance;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("accounts")
public class MongoAccount {

    @Id
    private String accountId;
    private double balance;

    public MongoAccount(final String accountId, final double balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public static MongoAccount from(final Account account) {
        return new MongoAccount(
                account.getAccountId().toString(),
                account.getBalance().getAmount());
    }

    public Account toAccount() {
        return new Account(
                AccountId.fromString(accountId),
                new Balance(balance));
    }
}
