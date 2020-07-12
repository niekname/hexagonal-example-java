package be.v86.hexagonalexample.domain;

public interface AccountRepository {

    Account findById(AccountId accountId) throws AccountNotFoundException;

    void save(Account account);

}
