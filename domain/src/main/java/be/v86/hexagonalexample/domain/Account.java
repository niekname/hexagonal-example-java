package be.v86.hexagonalexample.domain;

public class Account {

    private final AccountId accountId;
    private final Balance balance;

    public Account(final AccountId accountId) {
        this.accountId = accountId;
        this.balance = new Balance(0);
    }

    public Balance getBalance() {
        return balance;
    }

    public AccountId getAccountId() {
        return accountId;
    }
}
