package be.v86.hexagonalexample.mongo;

import be.v86.hexagonalexample.domain.Account;
import be.v86.hexagonalexample.domain.AccountId;
import be.v86.hexagonalexample.domain.AccountNotFoundException;
import be.v86.hexagonalexample.domain.AccountRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MongoAccountRepository implements AccountRepository {

    private MongoTemplate mongoTemplate;

    public MongoAccountRepository(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Account findById(final AccountId accountId) throws AccountNotFoundException {
        MongoAccount mongoAccount = mongoTemplate.findById(accountId.toString(), MongoAccount.class);
        if (mongoAccount == null)
            throw new AccountNotFoundException();
        return mongoAccount.toAccount();
    }

    @Override
    public void save(final Account account) {
        MongoAccount ma = MongoAccount.from(account);
        mongoTemplate.save(ma);
    }
}
