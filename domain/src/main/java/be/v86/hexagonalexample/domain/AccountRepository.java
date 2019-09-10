package be.v86.hexagonalexample.domain;

public interface AccountRepository {
    Account findOne(AccountId accountId);

    void save(Account account);
}
