package be.v86.hexagonalexample.rest;

import be.v86.hexagonalexample.application.CreateAccount;
import be.v86.hexagonalexample.application.Deposit;
import be.v86.hexagonalexample.application.DepositRequest;
import be.v86.hexagonalexample.application.InvalidAccountIdException;
import be.v86.hexagonalexample.application.InvalidAmountException;
import be.v86.hexagonalexample.domain.AccountId;
import be.v86.hexagonalexample.domain.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("accounts")
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    private final CreateAccount createAccount;
    private final Deposit deposit;

    public AccountController(final CreateAccount createAccount, final Deposit deposit) {
        this.createAccount = createAccount;
        this.deposit = deposit;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createAccount(final UriComponentsBuilder uriBuilder) {
        AccountId accountId = createAccount.execute();
        return ResponseEntity
                .created(uriBuilder
                        .pathSegment("accounts", "{id}")
                        .buildAndExpand(accountId.toString())
                        .toUri())
                .build();
    }

    @PostMapping(value = "/{iban}/deposit")
    public void deposit(@PathVariable String iban, @RequestBody DepositRequestJson body) {
        AccountId accountId = AccountId.fromString(iban);
        DepositRequest depositRequest = new DepositRequest(accountId, body.amount);
        deposit.execute(depositRequest);
    }

    @ExceptionHandler(value = {
            AccountRepository.AccountNotFoundException.class,
            InvalidAccountIdException.class,
            InvalidAmountException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void bad_request(Exception e) {
        LOGGER.info(e.getMessage(), e);
    }
}
