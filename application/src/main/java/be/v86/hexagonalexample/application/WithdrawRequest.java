package be.v86.hexagonalexample.application;

import be.v86.hexagonalexample.domain.AccountId;

public class WithdrawRequest {

    public AccountId accountId;
    public double amount;

    public WithdrawRequest(final AccountId accountId, final double amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

}
